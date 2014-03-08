package edu.sjsu.cmpe.library.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id","name"})
public class Author {
	
		
		private String name;
		private Long id;
		public Author(){
			//reviewKey =0;
		}
		
	
		public void setId(Long authorId){
			this.id = authorId;
		}
		public Long getId(){
			return id;
		}
		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name = name;
		}
}
