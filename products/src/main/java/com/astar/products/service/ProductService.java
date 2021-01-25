package com.astar.products.service;

import javax.transaction.Transactional;

import com.astar.products.enums.ProductStatusEnum;
import com.astar.products.enums.ResultEnum;
import com.astar.products.exception.ProductException;
import com.astar.products.model.ProductInfo;
import com.astar.products.repository.ProductInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class ProductService {

    
        @Autowired
        ProductInfoRepository productInfoRepository;
    
        @Autowired
        CategoryService categoryService;
    
        public ProductInfo findOne(String productId) {
    
            ProductInfo productInfo = productInfoRepository.findByProductId(productId);
            return productInfo;
        }
    
        public Page<ProductInfo> findUpAll(Pageable pageable) {
            return productInfoRepository.findAllByProductStatusOrderByProductIdAsc(ProductStatusEnum.UP.getCode(),pageable);
        }
    
        public Page<ProductInfo> findAll(Pageable pageable) {
            return productInfoRepository.findAllByOrderByProductId(pageable);
        }
    
        public Page<ProductInfo> findAllInCategory(Integer categoryType, Pageable pageable) {
            return productInfoRepository.findAllByCategoryTypeOrderByProductIdAsc(categoryType, pageable);
        }
    
        @Transactional
        public void increaseStock(String productId, int amount) {
            ProductInfo productInfo = findOne(productId);
            if (productInfo == null) throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
    
            int update = productInfo.getProductStock() + amount;
            productInfo.setProductStock(update);
            productInfoRepository.save(productInfo);
        }
    
        @Transactional
        public void decreaseStock(String productId, int amount) {
            ProductInfo productInfo = findOne(productId);
            if (productInfo == null) throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
    
            int update = productInfo.getProductStock() - amount;
            if(update <= 0) throw new ProductException(ResultEnum.PRODUCT_NOT_ENOUGH );
    
            productInfo.setProductStock(update);
            productInfoRepository.save(productInfo);
        }

        @Transactional
        public ProductInfo offSale(String productId) {
            ProductInfo productInfo = findOne(productId);
            if (productInfo == null) throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
    
            if (productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()) {
                throw new ProductException(ResultEnum.PRODUCT_STATUS_ERROR);
            }
    
            //更新
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
            return productInfoRepository.save(productInfo);
        }
 
        @Transactional
        public ProductInfo onSale(String productId) {
            ProductInfo productInfo = findOne(productId);
            if (productInfo == null) throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
    
            if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()) {
                throw new ProductException(ResultEnum.PRODUCT_STATUS_ERROR);
            }
    
            //更新
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            return productInfoRepository.save(productInfo);
        }
    
        public ProductInfo update(ProductInfo productInfo) {
    
            // if null throw exception
            categoryService.findByCategoryType(productInfo.getCategoryType());
            if(productInfo.getProductStatus() > 1) {
                throw new ProductException(ResultEnum.PRODUCT_STATUS_ERROR);
            }
    
    
            return productInfoRepository.save(productInfo);
        }

        public ProductInfo save(ProductInfo productInfo) {
            return update(productInfo);
        }

        public void delete(String productId) {
            ProductInfo productInfo = findOne(productId);
            if (productInfo == null) throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            productInfoRepository.delete(productInfo);
    
        }
    
    
    }