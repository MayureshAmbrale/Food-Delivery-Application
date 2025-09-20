package com.food_delivery.food_delivery.service;

import com.food_delivery.food_delivery.entity.FoodEntity;
import com.food_delivery.food_delivery.io.FoodRequest;
import com.food_delivery.food_delivery.io.FoodResponse;
import com.food_delivery.food_delivery.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService{

    private final S3Client s3Client;
    private final FoodRepository foodRepository;


    @Value("${aws.bucketName}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String key = UUID.randomUUID().toString()+"."+fileExtension;

        try
        {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();
            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            if(response.sdkHttpResponse().isSuccessful())
            {
                return "https://"+bucketName+".s3.amazonaws.com/"+key;
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File upload Fail");

            }

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred while uploading the file ");
        }
    }

    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
        FoodEntity foodEntity = convertToEntity(request);
        foodEntity.setImageUrl(uploadFile(file));
        foodEntity = foodRepository.save(foodEntity);

        return convertToResponse(foodEntity);
    }

    @Override
    public List<FoodResponse> readFood() {

        List<FoodEntity>  listFood = foodRepository.findAll();

        return listFood.stream().map(object -> convertToResponse(object)).toList();
    }

    @Override
    public FoodResponse readFood(String id) {
        FoodEntity existingFood = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("No food with this id: "+id));

        return convertToResponse(existingFood);
    }

    @Override
    public boolean deleteFile(String filename) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        return true;
    }

    @Override
    public void deleteFood(String id) {
        FoodResponse response = readFood(id);
        String imageUrl = response.getImageUrl();
        String fileName= imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        boolean isDeleted = deleteFile(fileName);
        if(isDeleted)
        {
            foodRepository.deleteById(id);
        }
    }

    public FoodResponse convertToResponse(FoodEntity entity)
    {
        return FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .category(entity.getCategory())
                .imageUrl(entity.getImageUrl())
                .build();
    }
    public FoodEntity convertToEntity(FoodRequest request)
    {
        return FoodEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .build();
    }


}
