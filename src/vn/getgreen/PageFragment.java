

package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.PageAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.imagecache.ImageFetcher;
import vn.getgreen.network.GClient;
import vn.getgreen.network.PostService;
import vn.getgreen.view.GImageView;
import vn.getgreen.view.GTextView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class PageFragment extends BaseFragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String ARG_TOTAL_PAGE = "total_page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private Thread mThread;
    private List<Post> posts = new ArrayList<Post>();
    private PostService mPostService;
    private PageAdapter mPageAdapter;
    private ListView mListPost;
    private PageListener pageListener;
    private int total_page;
   
    private LinearLayout header;
    private GImageView header_prev;
    private GImageView header_fast_prev;
    private GImageView header_next;
    private GImageView header_fast_next;
    private RelativeLayout header_page_btn;
    private GTextView header_page_list;
   
    private LinearLayout footer;
    private GImageView footer_prev;
    private GImageView footer_fast_prev;
    private GImageView footer_next;
    private GImageView footer_fast_next;
    private RelativeLayout footer_page_btn;

	private GTextView footer_page_list;

	public void setPageListener(PageListener pageListener) {
		this.pageListener = pageListener;
	}

	public abstract interface PageListener
    {
    	public abstract void onLoadComplete(int page,List<Post> post);
    	public abstract void onStartLoad();
    	public abstract void onPrevPage();
    	public abstract void onFastPrevPage();
    	public abstract void onNextPage();
    	public abstract void onFastNextPage();
    	public abstract void onPageBtn();
    }
    
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static PageFragment create(int pageNumber, Thread thread, PageListener pageListener, int totalPage, ImageFetcher imageFetcher) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putSerializable(Thread.class.getName(), thread);
        args.putInt(ARG_TOTAL_PAGE, totalPage);
        fragment.setArguments(args);
        fragment.setPageListener(pageListener);
        return fragment;
    }

    public PageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        total_page = getArguments().getInt(ARG_TOTAL_PAGE);
        mThread = (Thread) getArguments().getSerializable(Thread.class.getName());
        mPostService = new PostService(getActivity(), this);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_pages, container, false);
        header = (LinearLayout) inflater
                .inflate(R.layout.threadfootdark, null);
        footer = (LinearLayout) inflater
                .inflate(R.layout.threadfootdark, null);
        
        mListPost = (ListView) rootView.findViewById(R.id.list);
        
        header_prev = (GImageView) header.findViewById(R.id.prev);
        header_fast_prev = (GImageView) header.findViewById(R.id.fast_prev);
        header_next = (GImageView) header.findViewById(R.id.next);
        header_fast_next = (GImageView) header.findViewById(R.id.fast_next);
        header_page_btn = (RelativeLayout) header.findViewById(R.id.page_btn);
        header_page_list = (GTextView) header.findViewById(R.id.page_list);
        
        footer_prev = (GImageView) footer.findViewById(R.id.prev);
        footer_fast_prev = (GImageView) footer.findViewById(R.id.fast_prev);
        footer_next = (GImageView) footer.findViewById(R.id.next);
        footer_fast_next = (GImageView) footer.findViewById(R.id.fast_next);
        footer_page_btn = (RelativeLayout) footer.findViewById(R.id.page_btn);
        footer_page_list = (GTextView) footer.findViewById(R.id.page_list);
        setPageListener((PostsActivity)getActivity());
        if(total_page > 1)
        {
        	header_page_list.setText((mPageNumber + 1) + "/" + total_page);
            footer_page_list.setText((mPageNumber + 1) + "/" + total_page);
            mListPost.addHeaderView(header);
            if(mPageNumber == 0)
            {
            	header_prev.setEnabled(false);
            	header_fast_prev.setEnabled(false);
            	footer_prev.setEnabled(false);
            	footer_fast_prev.setEnabled(false);
            }
            if(mPageNumber == total_page - 1)
            {
            	header_fast_next.setEnabled(false);
            	header_next.setEnabled(false);
            	footer_fast_next.setEnabled(false);
            	footer_next.setEnabled(false);
            }
        }
        
        onRefresh();
        mPageAdapter = new PageAdapter(getActivity(), posts, ((PostsActivity) getActivity()).mImageFetcher);
        mListPost.setAdapter(mPageAdapter);
        if(total_page > 1)
        {
        	mListPost.addFooterView(footer);
        	header_prev.setOnClickListener(prevClick);
        	header_fast_prev.setOnClickListener(fastPrevClick);
        	header_next.setOnClickListener(nextClick);
        	header_fast_next.setOnClickListener(fastNextClick);
        	header_page_btn.setOnClickListener(pageBtnClick);
        	
        	footer_prev.setOnClickListener(prevClick);
        	footer_fast_prev.setOnClickListener(fastPrevClick);
        	footer_next.setOnClickListener(nextClick);
        	footer_fast_next.setOnClickListener(fastNextClick);
        	footer_page_btn.setOnClickListener(pageBtnClick);
        }
        
        return rootView;
    }

    OnClickListener prevClick = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			if(pageListener !=null) pageListener.onPrevPage();
		}
	};
	
	OnClickListener fastPrevClick = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			if(pageListener !=null) pageListener.onFastPrevPage();
		}
	};
	
	OnClickListener nextClick = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			if(pageListener !=null) pageListener.onNextPage();
		}
	};
	
	OnClickListener fastNextClick = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			if(pageListener !=null) pageListener.onFastNextPage();
		}
	};
	
	OnClickListener pageBtnClick = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			if(pageListener !=null) pageListener.onPageBtn();
		}
	};
	
	
    @Override
    public void onSuccess(GClient client, JSONObject jsonObject) {
    	if (client instanceof PostService && mPostService.parseJson(jsonObject)) {
			posts.clear();
			posts.addAll(mPostService.posts);
			mPageAdapter.notifyDataSetChanged();
			
			if (pageListener != null) {
				pageListener.onLoadComplete(mPageNumber, posts);
			}
		}
    	super.onSuccess(client, jsonObject);
    }
    
    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

	@Override
	public void onRefresh() {
		mPostService.listByThread(mThread, mPageNumber + 1, PostService.ORDER_NATURAL);
        if (pageListener != null) {
			pageListener.onStartLoad();
		}
	}
}
