package vn.getgreen.enties;

public class Category extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5148144934561970979L;
	public Category() {
		// TODO Auto-generated constructor stub
	}
	private int category_id;
	private String category_title;
	private String category_description;
	private Permissions permissions;
	private Links links;
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCategory_title() {
		return category_title;
	}
	public void setCategory_title(String category_title) {
		this.category_title = category_title;
	}
	public String getCategory_description() {
		return category_description;
	}
	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}
	public Permissions getPermissions() {
		return permissions;
	}
	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}
	public Links getLinks() {
		return links;
	}
	public void setLinks(Links links) {
		this.links = links;
	}
	
	
}
