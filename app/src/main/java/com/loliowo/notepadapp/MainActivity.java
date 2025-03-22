package com.loliowo.notepadapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.loliowo.notepadapp.adapter.NotepadListAdapter;
import com.loliowo.notepadapp.bean.Notepad;
import com.loliowo.notepadapp.databinding.ActivityMainBinding;
import com.loliowo.notepadapp.db.MyDbHelper;
import com.loliowo.notepadapp.ui.EditActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;

  //数据库帮助类
  private MyDbHelper myDbHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // 绑定到add按钮上，实现视图到跳转
    binding.addIv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, EditActivity.class));
      }
    });
    // 显然这里能转换为Lombok表达式

    // 显示所有数据库中存在的信息
    myDbHelper = new MyDbHelper(this);
    NotepadListAdapter notepadListAdapter = new NotepadListAdapter();
    binding.listRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    // 给listRv添加分割线效果
    binding.listRv.setAdapter(notepadListAdapter);
    // 对listRv设置Adapter
    List<Notepad> notepadList = myDbHelper.findAll();
    notepadListAdapter.update(notepadList);
  }
}