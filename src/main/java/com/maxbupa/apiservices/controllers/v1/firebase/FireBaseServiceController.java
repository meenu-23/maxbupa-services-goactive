package com.maxbupa.apiservices.controllers.v1.firebase;

import com.maxbupa.apiservices.model.product.request.FilePath;
import com.maxbupa.apiservices.services.firebase.FireBaseService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/maxbupa")
public class FireBaseServiceController {

    private final FireBaseService fireBaseService;

    public FireBaseServiceController(FireBaseService fireBaseService)
    {
        this.fireBaseService = fireBaseService;
    }

    //Insert Hospital data
    @PostMapping(value="/hospitalData")
    public void insertHospitalData(FilePath filePath)
    {
        fireBaseService.insertHospitalData(filePath.getFilePathOfProduct());
    }

}