package br.com.fiap.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "trip")
public class Trip {

    @DynamoDBHashKey(attributeName = "country")
    private String country;

    @DynamoDBRangeKey(attributeName = "dateTrip")
    private String dateTrip;

    @DynamoDBIndexRangeKey(attributeName = "city", localSecondaryIndexName = "cityIndex")
    private String city;

    @DynamoDBAttribute(attributeName = "reason")
    private String reason;

    public Trip(){
        super();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateTrip() {
        return dateTrip;
    }

    public void setDateTrip(String dateTrip) {
        this.dateTrip = dateTrip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
