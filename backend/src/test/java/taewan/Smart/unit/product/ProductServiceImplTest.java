package taewan.Smart.unit.product;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
//@TestPropertySource(locations="classpath:application.properties") 테스트에서 application properties 값 사용하기
class ProductServiceImplTest {
//
//    private static MockedStatic<FileUtil> mockedFileUtil;
//    @Mock private ProductDao productDao;
//    @InjectMocks private ProductServiceImpl productService;
//
//    private static List<ProductSaveDto> productSaveDtoList;
//    private static Map<String, Integer> codeInfo;
//
//    @BeforeAll
//    static void setup() {
//        productSaveDtoList = getProductSaveDtoList();
//        codeInfo = getDefaultCodeInfo();
//        mockedFileUtil = mockStatic(FileUtil.class);
//    }
//
//    @AfterAll
//    static void destroy() {
//        mockedFileUtil.close();
//    }
//
//    @AfterEach
//    void clean() {
//        deleteDirectory("images");
//    }
//
//    @Test
//    void 제품_저장() {
//        //given
//        String saved = "saved.jpeg";
//        ProductSaveDto dto = productSaveDtoList.get(0);
//        Product product = dto.toEntity(saved);
//
//        when(productDao.save(any())).thenReturn(product);
//        when(productDao.findByName(dto.getName())).thenReturn(Optional.empty());
//        when(saveFile(any(), anyString())).thenReturn(saved);
//        when(saveFiles(anyList(), anyString())).thenReturn(anyList());
//
//        //when
//        productService.save(dto);
//
//        //then
//        verify(productDao, times(1)).findByName(dto.getName());
//        verify(productDao, times(1)).save(any());
//        mockedFileUtil.verify(
//                () -> saveFile(any(), anyString()), times(1)
//        );
//        mockedFileUtil.verify(
//                () -> saveFiles(anyList(), anyString()), times(1)
//        );
//    }
//
//    @Test
//    void 중복된_제품명을_가진_제품_저장() {
//        //given
//        String saved = "saved.jpeg";
//        ProductSaveDto dto = productSaveDtoList.get(0);
//        Product product = dto.toEntity(saved);
//
//        when(productDao.findByName(dto.getName())).thenReturn(Optional.of(product));
//
//        //when //then
//        assertThrows(DuplicateKeyException.class, () -> productService.save(dto));
//        verify(productDao, times(1)).findByName(dto.getName());
//        verify(productDao, never()).save(any());
//        mockedFileUtil.verify(
//                () -> saveFile(any(), anyString()), never()
//        );
//        mockedFileUtil.verify(
//                () -> saveFiles(anyList(), anyString()), never()
//        );
//    }
//
//    @Test
//    void 제품_이미지가_누락된_제품_저장() {
//        //given
//        ProductSaveDto dto = productSaveDtoList.get(0);
//
//        when(productDao.findByName(dto.getName())).thenReturn(Optional.empty());
//        when(saveFiles(anyList(), anyString())).thenThrow(NullPointerException.class);
//
//        //when //then
//        assertThrows(IllegalArgumentException.class, () -> productService.save(dto));
//        verify(productDao, times(1)).findByName(dto.getName());
//        verify(productDao, never()).save(any());
//        mockedFileUtil.verify(
//                () -> saveFile(any(), anyString()), never()
//        );
//        mockedFileUtil.verify(
//                () -> saveFiles(anyList(), anyString()), times(1)
//        );
//    }
//
//    @Test
//    void 제품_단일조회() {
//        //given
//        String saved = "saved.jpeg";
//        ProductSaveDto dto = productSaveDtoList.get(0);
//        Product product = dto.toEntity(saved);
//        List<String> urls = Arrays.asList("url2", "url3", "url4");
//
//        when(productDao.findById(1L)).thenReturn(Optional.of(product));
//        when(getAccessUrl(anyString())).thenReturn("url1");
//        when(getAccessUrls(anyString())).thenReturn(urls);
//
//        //when
//        ProductInfoDto found = productService.findOne(1L);
//
//        //then
//        verify(productDao, times(1)).findById(1L);
//        mockedFileUtil.verify(
//                () -> getAccessUrl(anyString()), times(1)
//        );
//        mockedFileUtil.verify(
//                () -> getAccessUrls(anyString()), times(1)
//        );
//    }
//
//    @Test
//    void 없는_제품_단일조회() {
//        //given
//        when(productDao.findById(1L)).thenReturn(Optional.empty());
//
//        //when //then
//        assertThrows(NoSuchElementException.class, () -> productService.findOne(1L));
//        verify(productDao, times(1)).findById(1L);
//        mockedFileUtil.verify(
//                () -> getAccessUrl(anyString()), never()
//        );
//        mockedFileUtil.verify(
//                () -> getAccessUrls(anyString()), never()
//        );
//    }
//
//    @Test
//    void 페이지단위_조회() {
//        //given
//        List<ProductSaveDto> dto = productSaveDtoList;
//        List<Product> products = dto.stream().map(d -> d.toEntity("fileName")).collect(Collectors.toList());
//        Pageable pageable = PageRequest.of(0, dto.size());
//
//        when(productDao.findAll(pageable)).thenReturn(new PageImpl<>(products));
//        when(getAccessUrl(anyString())).thenReturn("url1");
//        when(getAccessUrls(anyString())).thenReturn(new ArrayList<>());
//
//        //when
//        Page<ProductInfoDto> result = productService.findAll(pageable);
//
//        //then
//        assertEquals(result.getTotalElements(), products.size());
//        verify(productDao, times(1)).findAll(pageable);
//        mockedFileUtil.verify(
//                () -> getAccessUrl(anyString()), times(products.size())
//        );
//        mockedFileUtil.verify(
//                () -> getAccessUrls(anyString()), times(products.size())
//        );
//    }
//
//    @Test
//    void Pageable_페이지단위_필터조회() {
////        //given
////        List<ProductSaveDto> productSaveDtos = productSaveDtoList;
////        List<Product> products = new ArrayList<>();
////        int page = 0;
////        int size = 5;
////        String code = "";
////        String search = "";
////        int resultSize = Math.min(size, productSaveDtoList.size());
////        Pageable pageable = PageRequest.of(page, size);
////
////        for (int i = 0; i < size; i++) {
////            ProductSaveDto dto = productSaveDtos.get(i);
////            products.add(dto.toEntity(saveImgFile(dto)));
////        }
////        when(productRepository.findAll(pageable)).thenReturn(toPage(products, pageable, productSaveDtos.size()));
////
////        //when
////        Page<ProductInfoDto> result = productService.findAllWithFilter(pageable, code, search);
////
////        //then
////        assertEquals(result.getTotalElements(), productSaveDtos.size());
////        assertEquals(result.getNumberOfElements(), resultSize);
////        result.getContent().forEach(p -> {
////            p.getImgFiles().forEach(f -> assertThat(f.contains(SERVER_ADDRESS)).isTrue());
////            assertThat(p.getDetailInfo().contains(SERVER_ADDRESS)).isTrue();
////        });
////        verify(productRepository, times(1)).findAll(pageable);
////        verify(productRepository, never()).findAllByCodeContains(pageable, code);
////        verify(productRepository, never()).findAllByNameContains(pageable, search);
////        verify(productRepository, never()).findAllByCodeContainsAndNameContains(pageable, code, search);
//    }
//
//    @Test
//    void Pageable과_분류번호를_포함한_페이지단위_필터조회() {
////        //given
////        List<ProductSaveDto> productSaveDtos = productSaveDtoList;
////        List<Product> products = new ArrayList<>();
////        Map<String, Integer> createdCodeInfo = codeInfo;
////        int page = 0;
////        int size = 5;
////        String code = productSaveDtos.get(0).getCode();
////        String search = "";
////        Pageable pageable = PageRequest.of(page, size);
////        int i = 0;
////
////        for (ProductSaveDto dto : productSaveDtos) {
////            if (dto.getCode().contains(code) && i++ < size) {
////                products.add(dto.toEntity(saveImgFile(dto)));
////            }
////        }
////        when(productRepository.findAllByCodeContains(pageable, code)).thenReturn(toPage(products, pageable, (long)createdCodeInfo.get(code)));
////
////        //when
////        Page<ProductInfoDto> result = productService.findAllWithFilter(pageable, code, search);
////
////        //then
////        assertEquals(result.getTotalElements(), (long)createdCodeInfo.get(code));
////        assertEquals(result.getNumberOfElements(), products.size());
////        result.getContent().forEach(p -> {
////            p.getImgFiles().forEach(f -> assertThat(f.contains(SERVER_ADDRESS)).isTrue());
////            assertThat(p.getDetailInfo().contains(SERVER_ADDRESS)).isTrue();
////        });
////        verify(productRepository, never()).findAll(pageable);
////        verify(productRepository, times(1)).findAllByCodeContains(pageable, code);
////        verify(productRepository, never()).findAllByNameContains(pageable, search);
////        verify(productRepository, never()).findAllByCodeContainsAndNameContains(pageable, code, search);
//    }
//
//    @Test
//    void Pageable과_검색어를_포함한_페이지단위_필터조회() {
////        //given
////        List<ProductSaveDto> productSaveDtos = productSaveDtoList;
////        List<Product> products = new ArrayList<>();
////        Map<String, Integer> createdCodeInfo = codeInfo;
////        int page = 0;
////        int size = 5;
////        String code = "";
////        String search = productSaveDtos.get(0).getName();
////        Pageable pageable = PageRequest.of(page, size);
////        int i = 0;
////        int total = 0;
////
////        for (ProductSaveDto dto : productSaveDtos) {
////            if (dto.getName().contains(search)) {
////                if (i++ < size)
////                    products.add(dto.toEntity(saveImgFile(dto)));
////                total++;
////            }
////        }
////        when(productRepository.findAllByNameContains(pageable, search)).thenReturn(toPage(products, pageable, total));
////
////        //when
////        Page<ProductInfoDto> result = productService.findAllWithFilter(pageable, code, search);
////
////        //then
////        assertEquals(result.getTotalElements(), total);
////        assertEquals(result.getNumberOfElements(), products.size());
////        result.getContent().forEach(p -> {
////            p.getImgFiles().forEach(f -> assertThat(f.contains(SERVER_ADDRESS)).isTrue());
////            assertThat(p.getDetailInfo().contains(SERVER_ADDRESS)).isTrue();
////        });
////        verify(productRepository, never()).findAll(pageable);
////        verify(productRepository, never()).findAllByCodeContains(pageable, code);
////        verify(productRepository, times(1)).findAllByNameContains(pageable, search);
////        verify(productRepository, never()).findAllByCodeContainsAndNameContains(pageable, code, search);
//    }
//
//    @Test
//    void Pageable과_검색어와_분류번호를_포함한_페이지단위_필터조회() {
////        //given
////        List<ProductSaveDto> productSaveDtos = productSaveDtoList;
////        List<Product> products = new ArrayList<>();
////        Map<String, Integer> createdCodeInfo = codeInfo;
////        int page = 0;
////        int size = 5;
////        String code = "";
////        String search = productSaveDtos.get(0).getName();
////        Pageable pageable = PageRequest.of(page, size);
////        int i = 0;
////        int total = 0;
////
////        for (ProductSaveDto dto : productSaveDtos) {
////            if (dto.getName().contains(search)) {
////                if (i++ < size)
////                    products.add(dto.toEntity(saveImgFile(dto)));
////                total++;
////            }
////        }
////        when(productRepository.findAllByNameContains(pageable, search)).thenReturn(toPage(products, pageable, total));
////
////        //when
////        Page<ProductInfoDto> result = productService.findAllWithFilter(pageable, code, search);
////
////        //then
////        assertEquals(result.getTotalElements(), total);
////        assertEquals(result.getNumberOfElements(), products.size());
////        result.getContent().forEach(p -> {
////            p.getImgFiles().forEach(f -> assertThat(f.contains(SERVER_ADDRESS)).isTrue());
////            assertThat(p.getDetailInfo().contains(SERVER_ADDRESS)).isTrue();
////        });
////        verify(productRepository, never()).findAll(pageable);
////        verify(productRepository, never()).findAllByCodeContains(pageable, code);
////        verify(productRepository, times(1)).findAllByNameContains(pageable, search);
////        verify(productRepository, never()).findAllByCodeContainsAndNameContains(pageable, code, search);
//    }
//
//    @Test
//    void 제품_수정() {
////        //given
////        ProductSaveDto saveDto = createProductSaveDto();
////        String fileName = saveImgFile(saveDto);
////        Product product = saveDto.toEntity(fileName);
////        ProductUpdateDto updateDto = createProductUpdateDto(2);
////
////        product.updateProduct(createProductUpdateDto(), fileName);
////
////        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
////        when(productRepository.findByName(dto.getName())).thenReturn(Optional.of(product));
////
////        //when
////        Long id = productService.update(dto);
////
////        //then
////        assertThat(id).isEqualTo(1L);
////        assertThat(product.toString().replace("Product", ""))
////                .isEqualTo(dto.toString().replace("ProductUpdateDto", ""));
////        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void 제품_삭제() {
//        //given
//        Product product =  productSaveDtoList.get(0).toEntity("fileName");
//
//        when(productDao.findById(1L)).thenReturn(Optional.of(product));
//        doNothing().when(productDao).deleteById(1L);
//
//
//        //when //then
//        productService.delete(1L);
//        verify(productDao, times(1)).findById(1L);
//        verify(productDao, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void 없는_제품_삭제() {
//        //given
//        ProductSaveDto dto = productSaveDtoList.get(0);
//        Product product = dto.toEntity(saveImgFile(dto));
//
//        when(productDao.findById(1L)).thenReturn(Optional.empty());
//
//        //when //then
//        assertThrows(NoSuchElementException.class, () ->
//                productService.delete(1L));
//        verify(productDao, times(1)).findById(1L);
//        verify(productDao, never()).deleteById(1L);
//    }
}