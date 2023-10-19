package com.humber.eap.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(namespace="https://humber.ca")



public class Stock {
    @JacksonXmlProperty(localName = "serialNum")
private int id;
    @JacksonXmlProperty
private String name;
    @JacksonXmlProperty
private double price;

    @JacksonXmlElementWrapper(useWrapping = true)
        private List<String> trends;


}
