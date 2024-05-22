package com.konada.Konada.response;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class ResponseService {
    public <T> SingleResponse<T> getSingleResponse(T data){
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data = data;
        if (data == null){
            setFailResponse(singleResponse);
        }else {
            setSuccessResponse(singleResponse);
        }
        return singleResponse;
    }

    public <T> ListResponse<T> getListResponse(List<T> dataList){
        ListResponse listResponse = new ListResponse();
        listResponse.dataList = dataList;
        if (dataList == null){
            setFailResponse(listResponse);
        }else {
            setSuccessResponse(listResponse);
        }
        return listResponse;
    }

    void setSuccessResponse(CommonResponse response){
        response.status = 1;
        response.success = true;
        response.message = "SUCCESS";
    }

    void setFailResponse(CommonResponse response){
        response.status = 2;
        response.success = false;
        response.message = "FAIL";
    }

}
