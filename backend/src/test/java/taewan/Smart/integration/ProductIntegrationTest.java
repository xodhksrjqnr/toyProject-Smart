package taewan.Smart.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ProductIntegrationTest {
//
//	@Autowired
//	private ProductService productService;
//	@Autowired
//	private ProductDao productDao;
//	@Autowired
//	private OrderItemRepository orderItemRepository;
//
//	@AfterEach
//	void destroy() {
//		deleteDirectory("products/");
//	}
//
//	@Test
//	void 제품_저장_테스트() {
//		//given
//		ProductSaveDto dto = getProductSaveDtoList().get(0);
//
//		//when
//		Long savedProductId = productService.save(dto);
//
//		//then
//		assertEquals(productDao.count(), 1L);
//		assertEquals(findFiles(dto.getDirectoryPath()).size(), 1);
//		assertEquals(findFiles(dto.getViewPath()).size(), dto.getImgFiles().size());
//	}
//
//	@Test
//	void 중복_제품명_저장_테스트() {
//		//given
//		ProductSaveDto dto1 = getProductSaveDtoList().get(0);
//		ProductSaveDto dto2 = getProductSaveDtoList().get(0);
//
//		//when //then
//		productService.save(dto1);
//		DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
//				() -> productService.save(dto2));
//		assertEquals(ex.getMessage(), PRODUCT_NAME_DUPLICATE.exception().getMessage());
//		assertEquals(findFiles(dto1.getDirectoryPath()).size(), 1);
//		assertEquals(findFiles(dto1.getViewPath()).size(), dto1.getImgFiles().size());
//	}
//
//	@Test
//	void 이미지_파일이_없는_제품_저장_테스트() {
//		//given
//		ProductSaveDto dto = ProductSaveDto.builder()
//				.imgFiles(getImgFiles(3))
//				.name("test")
//				.code("A01M")
//				.price(10000)
//				.size("s,m,l")
//				.detailInfo(new MockMultipartFile("none", new byte[0]))
//				.build();
//
//		//when //then
//		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
//				() -> productService.save(dto));
//		assertEquals(ex.getMessage(), PRODUCT_IMAGE_EMPTY.exception().getMessage());
//		assertEquals(findFiles(dto.getDirectoryPath()).size(), 0);
//	}
//
//	@Test
//	void 제품_조회_테스트() {
//		//given
//		ProductSaveDto dto = getProductSaveDtoList().get(0);
//		Long productId = productService.save(dto);
//
//		//when
//		ProductInfoDto found = productService.findOne(productId);
//
//		//then
//		assertEquals(productId, found.getProductId());
//		assertEquals(dto.getName(), found.getName());
//		assertEquals(dto.getPrice(), found.getPrice());
//		assertEquals(dto.getCode(), found.getCode());
//		assertEquals(dto.getSize(), found.getSize());
//		assertThat(found.getDetailInfo().contains(dto.getDirectoryPath())).isTrue();
//		found.getImgFiles().forEach(f -> {
//			assertThat(f).isNotNull();
//			assertThat(f.contains(dto.getViewPath())).isTrue();
//		});
//	}
//
//	@Test
//	void 없는_제품_조회_테스트() {
//		//when //then
//		NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//				() -> productService.findOne(1L));
//		assertEquals(ex.getMessage(), PRODUCT_NOT_FOUND.exception().getMessage());
//	}
//
//	@Test
//	void 제품_전체_조회_테스트() {
//		//given
//		List<ProductSaveDto> dtos = getProductSaveDtoList();
//		int page = 0;
//		int size = 5;
//
//		dtos.forEach(dto -> productService.save(dto));
//
//		//when
//		Page<ProductInfoDto> result = productService.findAll(PageRequest.of(page, size));
//		List<ProductInfoDto> found = result.getContent();
//
//		//then
//		assertEquals(result.getTotalElements(), getProductSaveDtoList().size());
//		assertEquals(result.getNumberOfElements(), size);
//		for (int i = 0; i < size; i++) {
//			ProductSaveDto dto = dtos.get(i);
//			ProductInfoDto dto2 = found.get(i);
//
//			assertEquals(dto.getName(), dto2.getName());
//			assertEquals(dto.getPrice(), dto2.getPrice());
//			assertEquals(dto.getCode(), dto2.getCode());
//			assertEquals(dto.getSize(), dto2.getSize());
//			assertThat(dto2.getDetailInfo().contains(dto.getDirectoryPath())).isTrue();
//			dto2.getImgFiles().forEach(f -> {
//				assertThat(f).isNotNull();
//				assertThat(f.contains(dto.getViewPath())).isTrue();
//			});
//		}
//	}
//
//	@Test
//	void 조건없는_필터_조회_테스트() {
//		//given
//		List<ProductSaveDto> dtos = getProductSaveDtoList();
//		List<ProductSaveDto> find = new ArrayList<>();
//		String code = "";
//		String search = "";
//		dtos.forEach(dto -> {
//			find.add(dto);
//			productService.save(dto);
//		});
//		int page = 0;
//		int size = 10;
//		int foundSize = Math.min(size, find.size());
//		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
//
//		//when
//		Page<ProductInfoDto> result = productService.findAllWithFilter(pageable, code, search);
//		List<ProductInfoDto> found = result.getContent();
//
//		find.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
//
//		//then
//		assertEquals(result.getTotalElements(), find.size());
//		assertEquals(result.getNumberOfElements(), foundSize);
//		for (int i = 0; i < foundSize; i++) {
//			ProductSaveDto fi = find.get(i);
//			ProductInfoDto fo = found.get(i);
//
//			assertEquals(fi.getName(), fo.getName());
//			assertEquals(fi.getPrice(), fo.getPrice());
//			assertEquals(fi.getCode(), fo.getCode());
//			assertEquals(fi.getSize(), fo.getSize());
//			assertThat(fo.getDetailInfo().contains(fi.getDirectoryPath())).isTrue();
//			fo.getImgFiles().forEach(f -> {
//				assertThat(f).isNotNull();
//				assertThat(f.contains(fi.getViewPath())).isTrue();
//			});
//		}
//	}
//
//	@Test
//	void 분류번호를_이용한_필터_조회_테스트() {
//		//given
//		List<ProductSaveDto> dtos = getProductSaveDtoList();
//		List<ProductSaveDto> find = new ArrayList<>();
//		String code = "A";
//		String search = "";
//		dtos.forEach(dto -> {
//			if (dto.getCode().contains(code))
//				find.add(dto);
//			productService.save(dto);
//		});
//		int page = 0;
//		int size = 10;
//		int foundSize = Math.min(size, find.size());
//		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
//
//		//when
//		Page<ProductInfoDto> result = productService.findAllWithFilter(pageable, code, search);
//		List<ProductInfoDto> found = result.getContent();
//
//		find.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
//
//		//then
//		assertEquals(result.getTotalElements(), find.size());
//		assertEquals(result.getNumberOfElements(), foundSize);
//		for (int i = 0; i < foundSize; i++) {
//			ProductSaveDto fi = find.get(i);
//			ProductInfoDto fo = found.get(i);
//
//			assertEquals(fi.getName(), fo.getName());
//			assertEquals(fi.getPrice(), fo.getPrice());
//			assertEquals(fi.getCode(), fo.getCode());
//			assertEquals(fi.getSize(), fo.getSize());
//			assertThat(fo.getDetailInfo().contains(fi.getDirectoryPath())).isTrue();
//			fo.getImgFiles().forEach(f -> {
//				assertThat(f).isNotNull();
//				assertThat(f.contains(fi.getViewPath())).isTrue();
//			});
//		}
//	}
//
//	@Test
//	void 검색어를_이용한_필터_조회_테스트() {
//		//given
//		List<ProductSaveDto> dtos = getProductSaveDtoList();
//		List<ProductSaveDto> find = new ArrayList<>();
//		String code = "";
//		String search = "product";
//		dtos.forEach(dto -> {
//			if (dto.getName().contains(search))
//				find.add(dto);
//			productService.save(dto);
//		});
//		int page = 0;
//		int size = 10;
//		int foundSize = Math.min(size, find.size());
//		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
//
//		//when
//		Page<ProductInfoDto> result = productService.findAllWithFilter(pageable, code, search);
//		List<ProductInfoDto> found = result.getContent();
//
//		find.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
//
//		//then
//		assertEquals(result.getTotalElements(), find.size());
//		assertEquals(result.getNumberOfElements(), foundSize);
//		for (int i = 0; i < foundSize; i++) {
//			ProductSaveDto fi = find.get(i);
//			ProductInfoDto fo = found.get(i);
//
//			assertEquals(fi.getName(), fo.getName());
//			assertEquals(fi.getPrice(), fo.getPrice());
//			assertEquals(fi.getCode(), fo.getCode());
//			assertEquals(fi.getSize(), fo.getSize());
//			assertThat(fo.getDetailInfo().contains(fi.getDirectoryPath())).isTrue();
//			fo.getImgFiles().forEach(f -> {
//				assertThat(f).isNotNull();
//				assertThat(f.contains(fi.getViewPath())).isTrue();
//			});
//		}
//	}
//
//	@Test
//	void 검색어와_분류번호를_이용한_필터_조회_테스트() {
//		//given
//		List<ProductSaveDto> dtos = getProductSaveDtoList();
//		List<ProductSaveDto> find = new ArrayList<>();
//		String code = "A";
//		String search = "product";
//		dtos.forEach(dto -> {
//			if (dto.getName().contains(search) && dto.getCode().contains(code))
//				find.add(dto);
//			productService.save(dto);
//		});
//		int page = 0;
//		int size = 10;
//		int foundSize = Math.min(size, find.size());
//		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
//
//		//when
//		Page<ProductInfoDto> result = productService.findAllWithFilter(pageable, code, search);
//		List<ProductInfoDto> found = result.getContent();
//
//		find.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
//
//		//then
//		assertEquals(result.getTotalElements(), find.size());
//		assertEquals(result.getNumberOfElements(), foundSize);
//		for (int i = 0; i < foundSize; i++) {
//			ProductSaveDto fi = find.get(i);
//			ProductInfoDto fo = found.get(i);
//
//			assertEquals(fi.getName(), fo.getName());
//			assertEquals(fi.getPrice(), fo.getPrice());
//			assertEquals(fi.getCode(), fo.getCode());
//			assertEquals(fi.getSize(), fo.getSize());
//			assertThat(fo.getDetailInfo().contains(fi.getDirectoryPath())).isTrue();
//			fo.getImgFiles().forEach(f -> {
//				assertThat(f).isNotNull();
//				assertThat(f.contains(fi.getViewPath())).isTrue();
//			});
//		}
//	}
//
//	@Test
//	void 제품_수정_테스트() {
//		//given
//		ProductSaveDto saveDto = getProductSaveDtoList().get(0);
//		Long productId = productService.save(saveDto);
//		ProductInfoDto saved = productService.findOne(productId);
//		ProductUpdateDto updateDto = ProductUpdateDto.builder()
//				.productId(productId)
//				.imgFiles(getImgFiles(3))
//				.name("test")
//				.price(30000)
//				.code("B01M")
//				.size("s")
//				.detailInfo(getImgFile())
//				.build();
//
//		//when
//		productService.update(updateDto);
//		ProductInfoDto updated = productService.findOne(productId);
//
//		//then
//		assertThat(saved.toString().equals(updated.toString())).isFalse();
//		assertEquals(findFiles(saveDto.getDirectoryPath()).size(), 1);
//		assertThat(findFiles(saveDto.getDirectoryPath()).get(0)).isNull();
//		assertEquals(findFiles(updateDto.getDirectoryPath()).size(), 1);
//		assertEquals(findFiles(updateDto.getViewPath()).size(), updateDto.getImgFiles().size());
//	}
//
//	@Test
//	void 없는_제품_수정_테스트() {
//		//given
//		ProductUpdateDto dto = ProductUpdateDto.builder()
//				.productId(1L)
//				.imgFiles(getImgFiles(3))
//				.name("test")
//				.price(30000)
//				.code("B01M")
//				.size("s")
//				.detailInfo(getImgFile())
//				.build();
//
//		//when //then
//		NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//				() -> productService.update(dto));
//		assertEquals(ex.getMessage(), PRODUCT_NOT_FOUND.exception().getMessage());
//	}
//
//	@Test
//	void 제품명과_productId를_제외한_제품_수정_테스트() {
//		//given
//		ProductSaveDto saveDto = getProductSaveDtoList().get(0);
//		Long productId = productService.save(saveDto);
//		ProductInfoDto saved = productService.findOne(productId);
//		ProductUpdateDto updateDto = ProductUpdateDto.builder()
//				.productId(productId)
//				.imgFiles(getImgFiles(3))
//				.name(saveDto.getName())
//				.price(30000)
//				.code("B01M")
//				.size("s")
//				.detailInfo(getImgFile())
//				.build();
//
//		//when
//		productService.update(updateDto);
//		ProductInfoDto updated = productService.findOne(productId);
//
//		//then
//		assertThat(saved.toString().equals(updated.toString())).isFalse();
//		assertEquals(findFiles(saveDto.getDirectoryPath()).size(), 1);
//		assertThat(findFiles(saveDto.getDirectoryPath()).get(0)).isNull();
//		assertEquals(findFiles(updateDto.getDirectoryPath()).size(), 1);
//		assertEquals(findFiles(updateDto.getViewPath()).size(), updateDto.getImgFiles().size());
//	}
//
//	@Test
//	void 수정된_제품명이_중복된_제품_수정_테스트() {
//		//given
//		ProductSaveDto saveDto1 = getProductSaveDtoList().get(0);
//		ProductSaveDto saveDto2 = getProductSaveDtoList().get(1);
//		productService.save(saveDto1);
//		Long productId = productService.save(saveDto2);
//		ProductUpdateDto updateDto = ProductUpdateDto.builder()
//				.productId(productId)
//				.imgFiles(getImgFiles(3))
//				.name(saveDto1.getName())
//				.price(30000)
//				.code("B01M")
//				.size("s")
//				.detailInfo(getImgFile())
//				.build();
//
//		//when //then
//		DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
//				() -> productService.update(updateDto));
//		assertEquals(ex.getMessage(), PRODUCT_NAME_DUPLICATE.exception().getMessage());
//		assertEquals(findFiles(saveDto2.getDirectoryPath()).size(), 1);
//		assertEquals(findFiles(saveDto2.getViewPath()).size(), saveDto2.getImgFiles().size());
//	}
//
//	@Test
//	void 이미지가_누락된_제품_수정_테스트() {
//		//given
//		ProductSaveDto saveDto = getProductSaveDtoList().get(0);
//		Long productId = productService.save(saveDto);
//		ProductUpdateDto updateDto = ProductUpdateDto.builder()
//				.productId(productId)
//				.imgFiles(getImgFiles(3))
//				.name("test")
//				.price(30000)
//				.code("B01M")
//				.size("s")
//				.detailInfo(new MockMultipartFile("test", new byte[0]))
//				.build();
//
//		//when //then
//		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
//				() -> productService.update(updateDto));
//		assertEquals(ex.getMessage(), PRODUCT_IMAGE_EMPTY.exception().getMessage());
//	}
//
//	@Test
//	void 제품_삭제_테스트() {
//		//given
//		ProductSaveDto dto = getProductSaveDtoList().get(0);
//
//		//when
//		Long savedProductId = productService.save(dto);
//		productService.delete(savedProductId);
//
//		//then
//		assertEquals(productDao.count(), 0);
//		List<String> found = findFiles(dto.getDirectoryPath());
//		assertEquals(found.size(), 1);
//		assertThat(found.get(0)).isNull();
//	}
//
//	@Test
//	void 없는_제품_삭제_테스트() {
//		//when //then
//		assertEquals(productDao.count(), 0);
//		NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//				() -> productService.delete(1L));
//		assertEquals(ex.getMessage(), PRODUCT_NOT_FOUND.exception().getMessage());
//	}
//
//	@Test
//	void 주문된_제품_삭제_테스트() {
//		//given
//		ProductSaveDto dto = getProductSaveDtoList().get(0);
//		Long savedProductId = productService.save(dto);
//		OrderItemSaveDto dto2 = new OrderItemSaveDto();
//
//		dto2.setProductId(savedProductId);
//		dto2.setSize("s");
//		dto2.setQuantity(2);
//
//		//when
//		orderItemRepository.save(OrderItem.createOrderItem(dto2, productDao.findById(savedProductId).get()));
//
//		//then
//		ForeignKeyException ex = assertThrows(ForeignKeyException.class,
//				() -> productService.delete(savedProductId));
//		assertEquals(ex.getMessage(), PRODUCT_REFERRED.exception().getMessage());
//	}
}
