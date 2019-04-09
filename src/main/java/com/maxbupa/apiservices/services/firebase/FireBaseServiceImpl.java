package com.maxbupa.apiservices.services.firebase;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseReference;
import com.maxbupa.apiservices.utilities.CSVConverter;
import com.maxbupa.apiservices.model.hospital.HospitalDTO;
import com.maxbupa.apiservices.model.hospital.HospitalLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireBaseServiceImpl implements FireBaseService{

    private DatabaseReference firebase;
    private static final String HOSPITAL_DATA = "hospitalsData";
    private static final String HOSPITAL_LOCALATION = "hospitalsLocation";

    @Autowired
    public FireBaseServiceImpl(DatabaseReference firebase)
    {
        this.firebase = firebase;
    }


    @Override
    public void insertHospitalData(String filePath) {
        firebase.child(HOSPITAL_DATA).removeValue(null);
        firebase.child(HOSPITAL_LOCALATION).removeValue(null);
        List<HospitalDTO> hospitalList = CSVConverter.processCsvToBean(filePath);
        List<HospitalLocation> hosLoc = new ArrayList<>();
        for(int i=0; i < hospitalList.size(); i++)
        {
            double latitude = hospitalList.get(i).getLatitude();
            double longitude = hospitalList.get(i).getLongitude();
            hosLoc.add(new HospitalLocation(latitude,longitude));
        }
        for(int i=0; i < hospitalList.size(); i++)
        {
            String userId = firebase.child(HOSPITAL_DATA).push().getKey();
            firebase.child(HOSPITAL_DATA).child(userId).setValue(hospitalList.get(i),null);
            GeoFire geoFire = new GeoFire(firebase.child(HOSPITAL_LOCALATION));
            geoFire.setLocation(userId, new GeoLocation(hosLoc.get(i).getLatitude(), hosLoc.get(i).getLongitude()));
        }
    }
}
