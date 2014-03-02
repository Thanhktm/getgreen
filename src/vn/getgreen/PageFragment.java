

package vn.getgreen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vn.getgreen.adapter.PageAdapter;
import vn.getgreen.common.BaseFragment;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.network.GClient;
import vn.getgreen.network.PostService;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PageFragment extends BaseFragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private Thread mThread;
    private List<Post> posts;
    private PostService mPostService;
    private PageAdapter mPageAdapter;
    private ListView mListPost;
    private PageListener pageListener;
    
    public void setPageListener(PageListener pageListener) {
		this.pageListener = pageListener;
	}

	public abstract interface PageListener
    {
    	public abstract void onLoadComplete();
    	public abstract void onStartLoad();
    }
    
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static PageFragment create(int pageNumber, Thread thread, PageListener pageListener) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putSerializable(Thread.class.getName(), thread);
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
        mThread = (Thread) getArguments().getSerializable(Thread.class.getName());
        mPostService = new PostService(getActivity(), this);
        mPostService.listByThread(mThread, mPageNumber, PostService.ORDER_NATURAL);
        if (pageListener != null) {
			pageListener.onStartLoad();
		}
        posts = new ArrayList<Post>();
        mPageAdapter = new PageAdapter(getActivity(), posts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_pages, container, false);
//        ViewGroup header_footer = (ViewGroup) inflater
//                .inflate(R.layout.threadfootdark, container, false);
        mListPost = (ListView) rootView.findViewById(R.id.list);
        mListPost.setAdapter(mPageAdapter);
//        mListPost.addHeaderView(header_footer);
//        mListPost.addFooterView(header_footer);
        

        return rootView;
    }

    @Override
    public void onSuccess(GClient client, JSONObject jsonObject) {
    	if (client instanceof PostService && mPostService.parseJson(jsonObject)) {
			posts.clear();
			posts.addAll(mPostService.posts);
			mPageAdapter.notifyDataSetChanged();
			if (pageListener != null) {
				pageListener.onLoadComplete();
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
		// TODO Auto-generated method stub
		
	}
}
