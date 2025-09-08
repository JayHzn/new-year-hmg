package com.example.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OrganizationCreateRequest {
    
    @NotBlank @Size(min = 2, max = 120)
    private String name;

    @Size(max = 2000)
    private String description;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String logoUrl;

    @Size(max = 255)
    private String website;

    private String ownerUserId;

    public String getName() { return name; }
    public void setName(String v) { name = v; }

    public String getDescription() { return description; }
    public void setDescription(String v) { description = v; }

    public String getAddress() { return address; }
    public void setAddress(String v) { address = v; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String v) { logoUrl = v; }

    public String getWebsite() { return website; }
    public void setWebsite(String v) { website = v; }

    public String getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(String v) { ownerUserId = v; }
}
