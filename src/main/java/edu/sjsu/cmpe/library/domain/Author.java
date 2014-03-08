package edu.sjsu.cmpe.library.domain;

public class Author {
	
		
		private String name;
		private Long authorId;
		public Author(){
			//reviewKey =0;
		}
		
	
		public void setAuthorId(Long authorId){
			this.authorId = authorId;
		}
		public Long getAuthorId(){
			return authorId;
		}
		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name = name;
		}
}
