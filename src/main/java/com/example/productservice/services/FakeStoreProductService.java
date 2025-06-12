package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.dtos.ProductCreateRequest;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    @Value("${api.host}")
    private String apiHost;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(apiHost +"products/" + id, FakeStoreProductDto.class);

        if(fakeStoreProductDto == null) {
            throw  new ProductNotFoundException(100L, "Product not found for id:" + id);
        }

        return getProductFromFakeStoreProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductsDto =  restTemplate.getForObject(apiHost + "products", FakeStoreProductDto[].class);
        return getProductsFromFakeStoreProducts(fakeStoreProductsDto);
    }

    @Override
    public Product replaceProduct(Long id, ProductCreateRequest product) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProduct = mapToFakeStoreProductDto(product);

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProduct, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto =  restTemplate.execute(apiHost + "products/" + id, HttpMethod.PUT, requestCallback, responseExtractor).getBody();

        return getProductFromFakeStoreProduct(fakeStoreProductDto);
    }

    @Override
    public Product createProduct(ProductCreateRequest product) {
        FakeStoreProductDto fakeStoreProduct = mapToFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductFromServer =  restTemplate.postForObject(apiHost + "products", fakeStoreProduct, FakeStoreProductDto.class);
        return getProductFromFakeStoreProduct(fakeStoreProductFromServer);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto =  restTemplate.execute(apiHost + "products/" + id, HttpMethod.DELETE, requestCallback, responseExtractor).getBody();

        if(fakeStoreProductDto == null) {
            throw  new ProductNotFoundException(100L, "Product not found for id:" + id);
        }

        return getProductFromFakeStoreProduct(fakeStoreProductDto);
    }

    private FakeStoreProductDto mapToFakeStoreProductDto(ProductCreateRequest product) {
        if (product == null) {
            return null;
        }
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory() != null ? product.getCategory().getTitle() : null);
        return dto;
    }

    private Product getProductFromFakeStoreProduct(FakeStoreProductDto fakeStoreProductDto) {
        if (fakeStoreProductDto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());

        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return  product;
    }

    private List<Product> getProductsFromFakeStoreProducts(FakeStoreProductDto[] fakeStoreProductsDto) {
        if (fakeStoreProductsDto == null || fakeStoreProductsDto.length == 0) {
            return List.of();
        }

        return Stream.of(fakeStoreProductsDto)
                .map(this::getProductFromFakeStoreProduct)
                .toList();
    }
}
