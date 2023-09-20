package br.com.communicationbetweensystems.grpc.controller;


import br.com.communicationbetweensystems.*;
import br.com.communicationbetweensystems.grpc.dto.ProductInputDTO;
import br.com.communicationbetweensystems.grpc.dto.ProductOutputDTO;
import br.com.communicationbetweensystems.grpc.service.ProductService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class ProductController extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductService productService;

    @Override
    public void create(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
       ProductInputDTO inputDTO = new ProductInputDTO(
                request.getName(),
                request.getPrice(),
                request.getQuantityInStock());

        ProductOutputDTO outputDTO = this.productService.create(inputDTO);

        ProductResponse response = ProductResponse.newBuilder()
                .setId(outputDTO.getId())
                .setName(outputDTO.getName())
                .setPrice(outputDTO.getPrice())
                .setQuantityInStock(outputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findById(RequestById request, StreamObserver<ProductResponse> responseObserver) {
       ProductOutputDTO outputDTO = productService.findByID(request.getId());
       ProductResponse response = ProductResponse.newBuilder()
                .setId(outputDTO.getId())
                .setName(outputDTO.getName())
                .setPrice(outputDTO.getPrice())
                .setQuantityInStock(outputDTO.getQuantityInStock())
                .build();

       responseObserver.onNext(response);
       responseObserver.onCompleted();
    }

    @Override
    public void delete(RequestById request, StreamObserver<EmptyREsponse> responseObserver) {
       productService.delete(request.getId());
       responseObserver.onNext(EmptyREsponse.newBuilder().build());
       responseObserver.onCompleted();
    }

    @Override
    public void findAll(EmptyRequest request, StreamObserver<ProductResponseList> responseObserver) {
        List<ProductOutputDTO> outputDTOList = productService.findAll();
        List<ProductResponse> productResponseList = outputDTOList.stream()
                .map(outputDTO ->
                        ProductResponse.newBuilder()
                                .setId(outputDTO.getId())
                                .setName(outputDTO.getName())
                                .setPrice(outputDTO.getPrice())
                                .setQuantityInStock(outputDTO.getQuantityInStock())
                                .build())
                .collect(Collectors.toList());

        ProductResponseList response = ProductResponseList.newBuilder()
                .addAllProducts(productResponseList)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
