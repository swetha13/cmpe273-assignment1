package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;
import java.util.List;

public class LinksDto {
    private List<LinkDto> links1 = new ArrayList<LinkDto>();
    
    public void addLink(LinkDto link) {
	links1.add(link);
    }

    
    /**
     * @return the links
     */
    public List<LinkDto> getLinks() {
	return links1;
    }

    /**
     * @param links
     *            the links to set
     */
    public void setLinks(List<LinkDto> links) {
	this.links1 = links;
    }

   
}
