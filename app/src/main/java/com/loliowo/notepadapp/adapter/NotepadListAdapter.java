package com.loliowo.notepadapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loliowo.notepadapp.MainActivity;
import com.loliowo.notepadapp.bean.Notepad;
import com.loliowo.notepadapp.databinding.ItemListBinding;
import com.loliowo.notepadapp.ui.EditActivity;

import java.util.ArrayList;
import java.util.List;

// 定义RecyclerView的适配器类，用于管理记事本列表数据的展示
public class NotepadListAdapter extends RecyclerView.Adapter<NotepadListAdapter.VH> {

  // 存储记事本数据的列表（使用ViewBinding后建议改为private）
  List<Notepad> list = new ArrayList<>();

  // 创建ViewHolder：当需要新列表项视图时调用
  @NonNull
  @Override
  public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // 使用LayoutInflater加载布局（通过ViewBinding实现）
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    // 通过生成的绑定类实例化视图
    ItemListBinding binding = ItemListBinding.inflate(layoutInflater);
    return new VH(binding); // 将绑定对象传递给ViewHolder
  }

  // 绑定数据到ViewHolder：将数据与列表项视图关联->从而进行数据更新
  @Override
  public void onBindViewHolder(@NonNull VH holder, int position) {
    Notepad notepad = list.get(position); // 获取当前位置对应的数据对象
    holder.binding.contentTv.setText(notepad.getContent()); // 设置内容文本
    holder.binding.timeTv.setText(notepad.getTime());       // 设置时间文本

    // 添加长按删除事件
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        showDeleteDialog(notepad, v.getContext());
        return false;
      }
    });

    // 添加点按编辑事件
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        MainActivity mainActivity=(MainActivity) v.getContext();
        Intent intent = new Intent(mainActivity, EditActivity.class);
        intent.putExtra("notepad",notepad);
        mainActivity.startActivityForResult(intent,100);
      }
    });
  }

  private void showDeleteDialog(Notepad notepad, Context context) {
    AlertDialog alertDialog = new AlertDialog.Builder(context)
      .setTitle("温馨提示")
      .setMessage("确定要删除这条内容吗？")
      .setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int i) {
          Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
        }
      })
      .setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int i) {
          Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
          MainActivity mainActivity = (MainActivity) context;
          mainActivity.myDbHelper.delete(notepad);
          // 这里涉及一个深层的context的实际内容：多多理解
          mainActivity.findAll();
          Toast.makeText(mainActivity, "删除成功", Toast.LENGTH_SHORT).show();
        }
      })
      .create();
    alertDialog.show();
    // 利用调试工具找打这里未使用show方法
  }

  // 获取列表项总数
  @Override
  public int getItemCount() {
    return list.size();
  }

  // 更新数据的方法（注意：建议使用DiffUtil优化大数据量更新），通过传入的list将改类的list更新
  @SuppressLint("NotifyDataSetChanged") // 忽略警告（实际开发中应优化此方法）
  public void update(List<Notepad> notepadList) {
    this.list.clear();       // 清空旧数据
    this.list.addAll(notepadList); // 添加新数据
    notifyDataSetChanged();  // 通知整个列表刷新（性能消耗较大）
  }

  // 自定义ViewHolder内部类
  class VH extends RecyclerView.ViewHolder {
    ItemListBinding binding; // 使用ViewBinding替代findViewById

    public VH(@NonNull ItemListBinding binding) {
      super(binding.getRoot()); // 必须调用父类构造器传入根视图
      this.binding = binding;     // 保存绑定对象引用
    }
  }
}