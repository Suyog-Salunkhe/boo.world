package com.example.boo.test.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProfileRequest {

    private Integer id;
    private String name;
    private String description;
    private String mbti;
    private String enneagram;
    private String variant;
    private Integer tritype;
    private String socionics;
    private String sloan;
    private String psyche;
    private String image;

    public CreateProfileRequest(Integer id, String name, String description, String mbti, String enneagram, String variant, Integer tritype, String socionics, String sloan, String psyche, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mbti = mbti;
        this.enneagram = enneagram;
        this.variant = variant;
        this.tritype = tritype;
        this.socionics = socionics;
        this.sloan = sloan;
        this.psyche = psyche;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getEnneagram() {
        return enneagram;
    }

    public void setEnneagram(String enneagram) {
        this.enneagram = enneagram;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Integer getTritype() {
        return tritype;
    }

    public void setTritype(Integer tritype) {
        this.tritype = tritype;
    }

    public String getSocionics() {
        return socionics;
    }

    public void setSocionics(String socionics) {
        this.socionics = socionics;
    }

    public String getSloan() {
        return sloan;
    }

    public void setSloan(String sloan) {
        this.sloan = sloan;
    }

    public String getPsyche() {
        return psyche;
    }

    public void setPsyche(String psyche) {
        this.psyche = psyche;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
