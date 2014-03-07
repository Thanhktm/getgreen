package vn.getgreen.enties;

import vn.getgreen.view.URLDrawable;

public class Photo extends BaseEnity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6996805845785920337L;
	public Photo() {
		// TODO Auto-generated constructor stub
	}

	private String url;
	private URLDrawable urlDrawable;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public URLDrawable getUrlDrawable() {
		return urlDrawable;
	}
	public void setUrlDrawable(URLDrawable urlDrawable) {
		this.urlDrawable = urlDrawable;
	}
	
}
