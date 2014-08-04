package vn.getgreen;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import vn.getgreen.common.BaseActivity;
import vn.getgreen.enties.Forum;
import vn.getgreen.enties.Post;
import vn.getgreen.enties.Thread;
import vn.getgreen.imagecache.Utils;
import vn.getgreen.network.AttachService;
import vn.getgreen.network.GClient;
import vn.getgreen.network.PostService;
import vn.getgreen.network.ThreadService;
import vn.getgreen.view.GEditText;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

public class NewTopicActivity extends BaseActivity {
	private ThreadService mThreadService;
	private PostService mPostNew;
	private AttachService mAttachService;
	
	
	private Thread mThread;
	private Post mPost;
	private GEditText mEditTitle;
	private GEditText mEditBody;
	private ImageButton mBtnAttach;
	private CheckBox mCkSignature;
	private Button mBtnImgTag;
	
	private int mode = -1;
	public static String MODE_CODE = "mode_code";

	public static final int MODE_NEW_TOPIC = 0;
	public static final int MODE_NEW_POST = 1;
	public static final int MODE_EDIT_POST = 2;
	public static final int MODE_NEW_POST_QUOTE = 4;
	

	private static final int SELECT_PICTURE = 1;
	private static final int CAMERA_REQUEST = 1888;

	private Uri mCapturedImageURI;
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtopic);
		// View init
		mEditTitle = (GEditText) findViewById(R.id.editsubject);
		mEditBody = (GEditText) findViewById(R.id.editcontent);
		mCkSignature = (CheckBox) findViewById(R.id.signature_tag);
		mBtnAttach = (ImageButton) findViewById(R.id.btn_attach);
		mBtnImgTag = (Button) findViewById(R.id.btn_img_tag);
		
		mBtnAttach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						NewTopicActivity.this);
				builder.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				int item;
				if (mPost == null || mPost.getPath() == null) {
					item = R.array.select_image_items;
				} else {
					item = R.array.select_image_items_clear;
				}
				builder.setItems(item, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0)
							openCamera();
						if (item == 1)
							openGalary();
						if (item == 2)
							clearImage();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		
		mBtnImgTag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int start = mEditBody.getSelectionStart(); //this is to get the the cursor position
				String s = "[IMG][/IMG]";
				mEditBody.getText().insert(start, s);
				mEditBody.setSelection(start + 5);
			}
		});
		
		mCkSignature.setText(signature);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		Intent intent = getIntent();
		if (intent.getSerializableExtra(Forum.class.getName()) != null) {
			Forum forum = (Forum) intent.getSerializableExtra(Forum.class
					.getName());
			mThread = new Thread();
			mThread.setForum_id(forum.getForum_id());
		}

		if (intent.getSerializableExtra(Thread.class.getName()) != null) {
			mThread = (Thread) intent.getSerializableExtra(Thread.class
					.getName());
		}
		if (intent.getSerializableExtra(Post.class.getName()) != null) {
			mPost = (Post) intent.getSerializableExtra(Post.class.getName());
		} else {
			mPost = new Post();
		}

		if (intent.getIntExtra(MODE_CODE, -1) != -1) {
			mode = intent.getIntExtra(MODE_CODE, -1);
		}

		switch (mode) {
		case MODE_NEW_TOPIC:
			mEditTitle.setVisibility(View.VISIBLE);
			break;
		case MODE_EDIT_POST:
			mEditTitle.setVisibility(View.GONE);
			mEditBody.setText(mPost.getPost_body());
			mEditBody.setSelection(mEditBody.getText().length());
			break;
		case MODE_NEW_POST:
			mEditTitle.setVisibility(View.GONE);
			break;
		case MODE_NEW_POST_QUOTE:
			mEditTitle.setVisibility(View.GONE);
			String quote = String.format(
					"[quote=\"%s, post: %d, member: %d\"]%s[/quote]\n\n",
					mPost.getPoster_username(), mPost.getPost_id(),
					mPost.getPoster_user_id(), mPost.getPost_body());
			mEditBody.setText(quote);
			mEditBody.setSelection(mEditBody.getText().length());
			break;
		default:
			break;
		}
		mThreadService = new ThreadService(this, this);
		mPostNew = new PostService(this, this);
		mAttachService = new AttachService(this, this);
	}

	@Override
	public void onStart(GClient client) {
		super.onStart(client);
		if(client == mThreadService || client == mPostNew){
			if(dialog == null){
				dialog = ProgressDialog.show(this, "", 
	                    "Loading. Please wait...", true);	
			} else {
				dialog.show();
			}
			
		}
	}
	
	@Override
	public void onFinish(GClient client) {
		if(mPost.getPath() == null){
			if(dialog != null) dialog.dismiss();
		}else{
			if(client == mAttachService){
				if(dialog != null) dialog.dismiss();
			}	
		}
		
		super.onFinish(client);
	}
	private void openGalary() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				SELECT_PICTURE);
	}

	private void openCamera() {
		String fileName = "temp.jpg";
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, fileName);
		mCapturedImageURI = getContentResolver().insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
		startActivityForResult(intent, CAMERA_REQUEST);
	}

	private void clearImage() {
		mBtnAttach.setImageResource(R.drawable.icon_attach);
		mPost.setPath(null);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_send).setVisible(true);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.action_send:
			switch (mode) {
			case MODE_NEW_TOPIC: {
				mPost.setPost_body(mEditBody.getText().toString()
						+ (mCkSignature.isChecked() ? "\n\n" + signature : ""));
				mThread.setFirst_post(mPost);
				mThread.setThread_title(mEditTitle.getText().toString());
				mThreadService.create(mThread);
			}
				break;
			case MODE_NEW_POST_QUOTE:
			case MODE_NEW_POST: {
				mPost.setPost_body(mEditBody.getText().toString()
						+ (mCkSignature.isChecked() ? "\n\n" + signature : ""));
				mPostNew.createPost(mThread, mPost.getPost_body());
			}
				break;
			case MODE_EDIT_POST: {
				mPost.setPost_body(mEditBody.getText().toString()
						+ (mCkSignature.isChecked() ? "\n\n" + signature : ""));
				mPostNew.editPost(mPost);
			}
				break;
			default:
				break;
			}
			return true;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onSuccess(GClient client, JSONObject jsonObject) {
		if (client instanceof ThreadService
				&& mThreadService.parseJson(jsonObject)) {
			if (mPost.getPath() == null) {
				setResult(RESULT_OK);
				finish();
			} else {
				mPost.setPost_id(mThreadService.thread.getFirst_post().getPost_id());
				mAttachService.attachFile(mPost);
			}
		}
		if (client == mPostNew && mPostNew.parseJson(jsonObject)) {
			if (mPost.getPath() == null) {
				setResult(RESULT_OK);
				finish();
			} else {
				mPost.setPost_id(mPostNew.post.getPost_id());
				mAttachService.attachFile(mPost);
			}
		}
		if(client == mAttachService)
		{
			setResult(RESULT_OK);
			finish();
		}
		super.onSuccess(client, jsonObject);
	}

	@Override
	public void onRefresh() {

	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == CAMERA_REQUEST) {

				String[] projection = { MediaStore.Images.Media.DATA };
				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(mCapturedImageURI, projection,
						null, null, null);
				int column_index_data = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				String capturedImageFilePath = cursor
						.getString(column_index_data);
				if(!Utils.checkSize(capturedImageFilePath)){
					Toast.makeText(this, R.string.limit_size, Toast.LENGTH_SHORT).show();
					return;
				}
				mPost.setPath(capturedImageFilePath);
				try {
					Bitmap photo = (Bitmap) MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), mCapturedImageURI);
					mBtnAttach.setImageBitmap(photo);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				if (Build.VERSION.SDK_INT < 19) {
					String selectedImagePath = getPath(selectedImageUri);
					if(!Utils.checkSize(selectedImagePath)){
						Toast.makeText(this, R.string.limit_size, Toast.LENGTH_SHORT).show();
						mPost.setPath(null);
						return;
					}
					Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
					mBtnAttach.setImageBitmap(bitmap);

				} else {
					ParcelFileDescriptor parcelFileDescriptor;
					try {
						parcelFileDescriptor = getContentResolver()
								.openFileDescriptor(selectedImageUri, "r");
						FileDescriptor fileDescriptor = parcelFileDescriptor
								.getFileDescriptor();
						Bitmap image = BitmapFactory
								.decodeFileDescriptor(fileDescriptor);
						parcelFileDescriptor.close();
						mPost.setPath(Utils.getPath(this, selectedImageUri));
						if(!Utils.checkSize(mPost.getPath())){
							Toast.makeText(this, R.string.limit_size, Toast.LENGTH_SHORT).show();
							mPost.setPath(null);
							return;
						}
						
						mBtnAttach.setImageBitmap(image);

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * helper to retrieve the path of an image URI
	 */
	public String getPath(Uri uri) {
		if (uri == null) {
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			mPost.setPath(cursor.getString(column_index));
			return mPost.getPath();
		}
		mPost.setPath(uri.getPath());
		return mPost.getPath();
	}

}
