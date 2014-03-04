package vn.getgreen.common;

import vn.getgreen.R;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ArrayAdapter;

public class DialogBuilder extends Builder {

	public interface GDialogListener extends OnClickListener
	{
		
	}
	public DialogBuilder(Context context, ArrayAdapter<String> adapter, GDialogListener callback)
	{
		super(context);
		setIcon(R.drawable.ic_launcher);
		setTitle(context.getResources().getString(R.string.perform_action));
		setNegativeButton(context.getResources().getString(R.string.menu_cancel),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
		setAdapter(adapter, callback);
		show();
	}
	public DialogBuilder(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DialogBuilder(Context arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
