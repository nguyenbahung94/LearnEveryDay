package com.hungnguyenba94gmail.learnskill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hungnguyenba94gmail.learnskill.createdialogfragment.GeneralDialogFragment;
import com.hungnguyenba94gmail.learnskill.createdialogfragment.OnDialogFragmentClickListener;

public class MainActivity extends AppCompatActivity implements OnDialogFragmentClickListener {
    private GeneralDialogFragment generalDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generalDialogFragment = GeneralDialogFragment.newInstance("Warning", "Do you want to delete it?");
        generalDialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onOkClicked(GeneralDialogFragment dialog) {
        Toast.makeText(this, "on Ok Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelClicked(GeneralDialogFragment dialog) {
        Toast.makeText(this, "on Cancel Clicked", Toast.LENGTH_SHORT).show();

    }
}
