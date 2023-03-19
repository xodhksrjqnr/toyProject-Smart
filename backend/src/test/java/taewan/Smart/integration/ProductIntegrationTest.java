package taewan.Smart.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.domain.order.repository.OrderItemDao;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.repository.ProductDao;
import taewan.Smart.domain.product.service.ProductService;
import taewan.Smart.fixture.ProductTestFixture;
import taewan.Smart.global.converter.PathConverter;
import taewan.Smart.global.exception.ForeignKeyException;
import taewan.Smart.global.util.PropertyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static taewan.Smart.fixture.ProductTestFixture.*;
import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.util.CustomFileUtils.deleteDirectory;
import static taewan.Smart.global.util.CustomFileUtils.findFilePaths;

@SpringBootTest
@Transactional
class ProductIntegrationTest {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private OrderItemDao orderItemDao;
	@Value("${path.testImg}")
	private String testImgPath;

	@AfterEach
	void destroy() {
		deleteDirectory(PropertyUtils.getImgFolderPath() + "products");
	}

	@Test
	@DisplayName("제품 저장 테스트")
	void save() {
		//given
		ProductSaveDto dto = createProductSaveDto(testImgPath);
		int size = dto.getImgFiles().size() + 1;

		//when
		Long savedId = productService.save(dto);
		List<String> found = findFilePaths(PathConverter.toImgAccessLocal(dto.getImgSavePath()));

		//then
		assertEquals(productDao.count(), 1L);
		assertEquals(found.size(), size);
	}

	@Test
	@DisplayName("저장하려는 제품의 제품명이 중복일 때 저장 테스트")
	void save_duplicate_name() {
		//given
		ProductSaveDto dto1 = createProductSaveDto(1, testImgPath);
		ProductSaveDto dto2 = ProductSaveDto.builder()
				.imgFiles(getImgFiles(3, testImgPath))
				.name(dto1.getName())
				.price(1000)
				.code("A02M")
				.size("s,m,l,xl,xxl")
				.detailInfo(getImgFiles(1, testImgPath).get(0))
				.build();

		//when //then
		productService.save(dto1);

		List<String> found = findFilePaths(PathConverter.toImgAccessLocal(dto2.getImgSavePath()));

		DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
				() -> productService.save(dto2));
		assertEquals(ex.getMessage(), PRODUCT_NAME_DUPLICATE.exception().getMessage());
		assertEquals(found.size(), 0);
	}

	@Test
	@DisplayName("이미지 파일이 유효하지 않은 제품 저장 테스트")
	void save_invalid_imgFile() {
		//given
		ProductSaveDto dto = ProductSaveDto.builder()
				.imgFiles(getImgFiles(3, testImgPath))
				.name("test")
				.code("A01M")
				.price(10000)
				.size("s,m,l")
				.detailInfo(new MockMultipartFile("none", new byte[0]))
				.build();

		//when
		List<String> found = findFilePaths(PathConverter.toImgAccessLocal(dto.getImgSavePath()));

		//then
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> productService.save(dto));
		assertEquals(ex.getMessage(), PRODUCT_IMAGE_EMPTY.exception().getMessage());
		assertEquals(found.size(), 0);
	}

	@Test
	@DisplayName("제품 조회 테스트")
	void find() {
		//given
		ProductSaveDto dto = createProductSaveDto(testImgPath);
		Long productId = productService.save(dto);
		int size = dto.getImgFiles().size() + 1;

		//when
		ProductInfoDto found = productService.findOne(productId);
		List<String> images = findFilePaths(PathConverter.toImgAccessLocal(dto.getImgSavePath()));

		//then
		assertEquals(productId, found.getProductId());
		assertEquals(dto.getName(), found.getName());
		assertEquals(dto.getPrice(), found.getPrice());
		assertEquals(dto.getCode(), found.getCode());
		assertEquals(dto.getSize(), found.getSize());
		assertEquals(images.size(), size);
	}

	@Test
	@DisplayName("없는 제품 조회 테스트")
	void find_invalid() {
		//when //then
		NoSuchElementException ex = assertThrows(NoSuchElementException.class,
				() -> productService.findOne(1L));
		assertEquals(ex.getMessage(), PRODUCT_NOT_FOUND.exception().getMessage());
	}

	@Test
	@DisplayName("조건없는 필터 조회 테스트")
	void findAll_filter() {
		//given
		List<ProductSaveDto> dtos = createProductSaveDtoList(10, testImgPath);
		List<ProductSaveDto> find = new ArrayList<>();
		String code = "";
		String search = "";
		dtos.forEach(dto -> {
			find.add(dto);
			productService.save(dto);
		});
		int page = 0;
		int size = 10;
		int foundSize = Math.min(size, find.size());
		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

		//when
		Page<ProductInfoDto> result = productService.findAllByFilter(pageable, code, search);
		List<ProductInfoDto> found = result.getContent();

		find.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

		//then
		assertEquals(result.getTotalElements(), find.size());
		assertEquals(result.getNumberOfElements(), foundSize);
		for (int i = 0; i < foundSize; i++) {
			ProductSaveDto fi = find.get(i);
			ProductInfoDto fo = found.get(i);

			assertEquals(fi.getName(), fo.getName());
			assertEquals(fi.getPrice(), fo.getPrice());
			assertEquals(fi.getCode(), fo.getCode());
			assertEquals(fi.getSize(), fo.getSize());
			assertEquals(fi.getImgFiles().size(), fo.getImgFiles().size());
			assertThat(fo.getDetailInfo()).isNotEmpty();
		}
	}

	@Test
	@DisplayName("분류코드를 이용한 필터 조회 테스트")
	void findAll_filter_code() {
		//given
		List<ProductSaveDto> dtos = createProductSaveDtoList(10, testImgPath);
		List<ProductSaveDto> find = new ArrayList<>();
		String code = "A";
		String search = "";
		dtos.forEach(dto -> {
			if (dto.getCode().contains(code))
				find.add(dto);
			productService.save(dto);
		});
		int page = 0;
		int size = 10;
		int foundSize = Math.min(size, find.size());
		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

		//when
		Page<ProductInfoDto> result = productService.findAllByFilter(pageable, code, search);
		List<ProductInfoDto> found = result.getContent();

		find.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

		//then
		assertEquals(result.getTotalElements(), find.size());
		assertEquals(result.getNumberOfElements(), foundSize);
		for (int i = 0; i < foundSize; i++) {
			ProductSaveDto fi = find.get(i);
			ProductInfoDto fo = found.get(i);

			assertEquals(fi.getName(), fo.getName());
			assertEquals(fi.getPrice(), fo.getPrice());
			assertEquals(fi.getCode(), fo.getCode());
			assertEquals(fi.getSize(), fo.getSize());
			assertEquals(fi.getImgFiles().size(), fo.getImgFiles().size());
			assertThat(fo.getDetailInfo()).isNotEmpty();
		}
	}

	@Test
	@DisplayName("검색어를 이용한 필터 조회 테스트")
	void findAll_filter_search() {
		//given
		List<ProductSaveDto> dtos = createProductSaveDtoList(10, testImgPath);
		List<ProductSaveDto> find = new ArrayList<>();
		String code = "";
		String search = "product";
		dtos.forEach(dto -> {
			if (dto.getName().contains(search))
				find.add(dto);
			productService.save(dto);
		});
		int page = 0;
		int size = 10;
		int foundSize = Math.min(size, find.size());
		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

		//when
		Page<ProductInfoDto> result = productService.findAllByFilter(pageable, code, search);
		List<ProductInfoDto> found = result.getContent();

		find.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

		//then
		assertEquals(result.getTotalElements(), find.size());
		assertEquals(result.getNumberOfElements(), foundSize);
		for (int i = 0; i < foundSize; i++) {
			ProductSaveDto fi = find.get(i);
			ProductInfoDto fo = found.get(i);

			assertEquals(fi.getName(), fo.getName());
			assertEquals(fi.getPrice(), fo.getPrice());
			assertEquals(fi.getCode(), fo.getCode());
			assertEquals(fi.getSize(), fo.getSize());
			assertEquals(fi.getImgFiles().size(), fo.getImgFiles().size());
			assertThat(fo.getDetailInfo()).isNotEmpty();
		}
	}

	@Test
	@DisplayName("검색어와 분류코드를 이용한 필터 조회 테스트")
	void findAll_filter_search_and_code() {
		//given
		List<ProductSaveDto> dtos = createProductSaveDtoList(10, testImgPath);
		List<ProductSaveDto> find = new ArrayList<>();
		String code = "A";
		String search = "product";
		dtos.forEach(dto -> {
			if (dto.getName().contains(search) && dto.getCode().contains(code))
				find.add(dto);
			productService.save(dto);
		});
		int page = 0;
		int size = 10;
		int foundSize = Math.min(size, find.size());
		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

		//when
		Page<ProductInfoDto> result = productService.findAllByFilter(pageable, code, search);
		List<ProductInfoDto> found = result.getContent();

		find.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

		//then
		assertEquals(result.getTotalElements(), find.size());
		assertEquals(result.getNumberOfElements(), foundSize);
		for (int i = 0; i < foundSize; i++) {
			ProductSaveDto fi = find.get(i);
			ProductInfoDto fo = found.get(i);

			assertEquals(fi.getName(), fo.getName());
			assertEquals(fi.getPrice(), fo.getPrice());
			assertEquals(fi.getCode(), fo.getCode());
			assertEquals(fi.getSize(), fo.getSize());
			assertEquals(fi.getImgFiles().size(), fo.getImgFiles().size());
			assertThat(fo.getDetailInfo()).isNotEmpty();
		}
	}

	@Test
	@DisplayName("제품 정보 수정 테스트")
	void update() {
		//given
		ProductSaveDto saveDto = createProductSaveDto(testImgPath);
		Long productId = productService.save(saveDto);
		ProductInfoDto saved = productService.findOne(productId);
		ProductUpdateDto updateDto = ProductUpdateDto.builder()
				.productId(productId)
				.imgFiles(getImgFiles(3, testImgPath))
				.name("test")
				.price(30000)
				.code("B01M")
				.size("s")
				.detailInfo(getImgFiles(1, testImgPath).get(0))
				.build();
		int size = updateDto.getImgFiles().size() + 1;

		//when
		productService.update(updateDto);

		ProductInfoDto updated = productService.findOne(productId);
		List<String> savedImages = findFilePaths(PathConverter.toImgAccessLocal(saveDto.getImgSavePath()));
		List<String> updatedImages = findFilePaths(PathConverter.toImgAccessLocal(updateDto.getImgSavePath()));

		//then
		assertThat(saved.toString().equals(updated.toString())).isFalse();
		assertEquals(savedImages.size(), 0);
		assertEquals(updatedImages.size(), size);
	}

	@Test
	@DisplayName("유효하지 않은 제품 정보 수정 테스트")
	void update_invalid() {
		//given
		ProductUpdateDto dto = ProductUpdateDto.builder()
				.productId(1L)
				.imgFiles(getImgFiles(3, testImgPath))
				.name("test")
				.price(30000)
				.code("B01M")
				.size("s")
				.detailInfo(getImgFiles(1, testImgPath).get(0))
				.build();

		//when //then
		NoSuchElementException ex = assertThrows(NoSuchElementException.class,
				() -> productService.update(dto));
		assertEquals(ex.getMessage(), PRODUCT_NOT_FOUND.exception().getMessage());
	}

	@Test
	@DisplayName("제품명 외의 정보를 수정한 제품 수정 테스트")
	void update_exclude_name() {
		//given
		ProductSaveDto saveDto = createProductSaveDto(testImgPath);
		Long productId = productService.save(saveDto);
		ProductInfoDto saved = productService.findOne(productId);
		ProductUpdateDto updateDto = ProductUpdateDto.builder()
				.productId(productId)
				.imgFiles(getImgFiles(3, testImgPath))
				.name(saveDto.getName())
				.price(30000)
				.code("B01M")
				.size("s")
				.detailInfo(getImgFiles(1, testImgPath).get(0))
				.build();
		int size = updateDto.getImgFiles().size() + 1;

		//when
		productService.update(updateDto);

		ProductInfoDto updated = productService.findOne(productId);
		List<String> savedImages = findFilePaths(PathConverter.toImgAccessLocal(saveDto.getImgSavePath()));
		List<String> updatedImages = findFilePaths(PathConverter.toImgAccessLocal(updateDto.getImgSavePath()));

		//then
		assertNotEquals(
				ProductTestFixture.toString(saved),
				ProductTestFixture.toString(updated)
		);
		assertEquals(savedImages.size(), 0);
		assertEquals(updatedImages.size(), size);
	}

	@Test
	@DisplayName("수정하려는 제품명이 이미 다른 제품이 등록한 제품명인 경우의 제품 수정 테스트")
	void update_duplicate_name() {
		//given
		ProductSaveDto saveDto1 = createProductSaveDto(1, testImgPath);
		ProductSaveDto saveDto2 = createProductSaveDto(2, testImgPath);
		productService.save(saveDto1);
		Long productId = productService.save(saveDto2);
		ProductInfoDto before = productService.findOne(productId);
		ProductUpdateDto updateDto = ProductUpdateDto.builder()
				.productId(productId)
				.imgFiles(getImgFiles(3, testImgPath))
				.name(saveDto1.getName())
				.price(30000)
				.code("B01M")
				.size("s")
				.detailInfo(getImgFiles(1, testImgPath).get(0))
				.build();
		int dto1Size = saveDto1.getImgFiles().size() + 1;
		int dto2Size = saveDto2.getImgFiles().size() + 1;

		//when
		List<String> saved1Images = findFilePaths(PathConverter.toImgAccessLocal(saveDto1.getImgSavePath()));
		List<String> saved2Images = findFilePaths(PathConverter.toImgAccessLocal(saveDto2.getImgSavePath()));
		List<String> updatedImages = findFilePaths(PathConverter.toImgAccessLocal(updateDto.getImgSavePath()));

		//then
		DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
				() -> productService.update(updateDto));
		assertEquals(ex.getMessage(), PRODUCT_NAME_DUPLICATE.exception().getMessage());

		ProductInfoDto after = productService.findOne(productId);

		assertEquals(
				ProductTestFixture.toString(before),
				ProductTestFixture.toString(after)
		);
		assertEquals(saved1Images.size(), dto1Size);
		assertEquals(saved2Images.size(), dto2Size);
		assertEquals(updatedImages.size(), 0);
	}

	@Test
	@DisplayName("이미지가 누락된 상태에서의 제품 수정 테스트")
	void update_invalid_image() {
		//given
		ProductSaveDto saveDto = createProductSaveDto(testImgPath);
		Long productId = productService.save(saveDto);
		ProductUpdateDto updateDto = ProductUpdateDto.builder()
				.productId(productId)
				.imgFiles(getImgFiles(3, testImgPath))
				.name("test")
				.price(30000)
				.code("B01M")
				.size("s")
				.detailInfo(new MockMultipartFile("test", new byte[0]))
				.build();

		//when //then
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> productService.update(updateDto));
		assertEquals(ex.getMessage(), PRODUCT_IMAGE_EMPTY.exception().getMessage());
	}

	@Test
	@DisplayName("제품 삭제 테스트")
	void delete() {
		//given
		ProductSaveDto dto = createProductSaveDto(testImgPath);

		//when
		Long savedProductId = productService.save(dto);
		productService.delete(savedProductId);

		//then
		assertEquals(productDao.count(), 0);
		List<String> found = findFilePaths(PathConverter.toImgAccessLocal(dto.getImgSavePath()));
		assertEquals(found.size(), 0);
	}

	@Test
	@DisplayName("존재하지 않는 제품 삭제 테스트")
	void delete_invalid() {
		//when //then
		assertEquals(productDao.count(), 0);
		NoSuchElementException ex = assertThrows(NoSuchElementException.class,
				() -> productService.delete(1L));
		assertEquals(ex.getMessage(), PRODUCT_NOT_FOUND.exception().getMessage());
	}

	@Test
	@DisplayName("주문이 들어간 제품 삭제 테스트")
	void delete_ordered() {
		//given
		ProductSaveDto dto = createProductSaveDto(testImgPath);
		Long savedProductId = productService.save(dto);
		OrderItemSaveDto dto2 = new OrderItemSaveDto(savedProductId, "s", 2);

		//when
		orderItemDao.save(OrderItem.createOrderItem(dto2, productDao.findById(savedProductId).get()));

		//then
		ForeignKeyException ex = assertThrows(ForeignKeyException.class,
				() -> productService.delete(savedProductId));
		assertEquals(ex.getMessage(), PRODUCT_REFERRED.exception().getMessage());
	}
}
