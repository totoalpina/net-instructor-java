package ro.netinstructor.models;

import javax.validation.constraints.NotBlank;

public class CompanyDto {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String cif;

    public CompanyDto() {
    }

    public CompanyDto(String name, String address, String cif) {
        this.name = name;
        this.address = address;
        this.cif = cif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }
}
