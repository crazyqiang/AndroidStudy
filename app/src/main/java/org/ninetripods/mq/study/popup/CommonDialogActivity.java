package org.ninetripods.mq.study.popup;

import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ninetripods.sydialoglib.IDialog;
import com.ninetripods.sydialoglib.SYDialog;
import com.ninetripods.sydialoglib.manager.DialogWrapper;
import com.ninetripods.sydialoglib.manager.SYDialogsManager;
import com.ninetripods.sydialoglib.widget.DialogLoadingView;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.MyApplication;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.popup.dialog.DialogUtil;

public class CommonDialogActivity extends BaseActivity {
    private SYDialog dialog;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_common_dialog);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "CommonDialog", true);
    }

    @Override
    public void initEvents() {
        super.initEvents();
    }

    /**
     * 1、默认Dialog:一个Button
     *
     * @param view View
     */
    public void showDefaultDialog(View view) {

        DialogUtil.createDefaultDialog(this, "我是标题", "你好,我们将在30分钟处理，稍后通知您订单结果！",
                "", new IDialog.OnClickListener() {
                    @Override
                    public void onClick(IDialog dialog) {
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 2、默认Dialog:二个Button
     *
     * @param view View
     */
    public void showDefaultDialogTwo(View view) {

        DialogUtil.createDefaultDialog(this, "分享", "分享此接单码，您和乘客都将获得现金红包！",
                "立即分享", new IDialog.OnClickListener() {
                    @Override
                    public void onClick(IDialog dialog) {
                        Toast.makeText(MyApplication.getApplication(), "立即分享", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                },
                "有钱不要", new IDialog.OnClickListener() {
                    @Override
                    public void onClick(IDialog dialog) {
                        Toast.makeText(MyApplication.getApplication(), "有钱不要", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }


    /**
     * 3、自定义Dialog 基本使用
     *
     * @param view View
     */
    public void showBaseUseDialog(View view) {
        new SYDialog.Builder(this)
                .setDialogView(R.layout.layout_dialog)//设置dialog布局
                .setAnimStyle(R.style.translate_style)//设置动画 默认没有动画
                .setScreenWidthP(0.85f) //设置屏幕宽度比例 0.0f-1.0f
                .setGravity(Gravity.CENTER)//设置Gravity
                .setWindowBackgroundP(0.2f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                .setCancelable(true)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                .setCancelableOutSide(true)//设置dialog外点击是否可以让dialog消失
                .setOnDismissListener(new IDialog.OnDismissListener() {
                    @Override
                    public void onDismiss(IDialog dialog) {
                        //监听dialog dismiss的回调
                        toast("dismiss回调");
                    }
                })
                .setBuildChildListener(new IDialog.OnBuildListener() {
                    //设置子View
                    @Override
                    public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                        //dialog: IDialog
                        //view： DialogView
                        //layoutRes :Dialog的资源文件 如果一个Activity里有多个dialog 可以通过layoutRes来区分
                        final EditText editText = view.findViewById(R.id.et_content);
                        Button btn_ok = view.findViewById(R.id.btn_ok);
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String editTextStr = null;
                                if (!TextUtils.isEmpty(editText.getText())) {
                                    editTextStr = editText.getText().toString();
                                } else {
                                    editTextStr = "EditText输入为空";
                                }
                                dialog.dismiss();
                                Toast.makeText(MyApplication.getApplication(), editTextStr, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).show();
    }

    /**
     * 4、展示进度条
     *
     * @param view View
     */

    public void showLoadingDialog(View view) {
        DialogUtil.createLoadingDialog(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogUtil.closeLoadingDialog(CommonDialogActivity.this);
            }
        }, 5000);
    }

    /**
     * 5、全屏广告弹窗
     *
     * @param view View
     */
    public void showAdDialog(View view) {
        new SYDialog.Builder(this)
                .setDialogView(R.layout.layout_ad_dialog)
                .setWindowBackgroundP(0.5f)
                .setAnimStyle(0)
                .setBuildChildListener(new IDialog.OnBuildListener() {
                    @Override
                    public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                        ImageView iv_close = view.findViewById(R.id.iv_close);
                        iv_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        ImageView iv_ad = view.findViewById(R.id.iv_ad);
                        iv_ad.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "点击广告", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                }).show();
    }

    /**
     * 6、底部选择弹窗
     *
     * @param view View
     */
    public void showBottomDialog(View view) {
        new SYDialog.Builder(this)
                .setDialogView(R.layout.layout_bottom_up)
                .setWindowBackgroundP(0.5f)
                .setAnimStyle(R.style.AnimUp)
                .setCancelableOutSide(true)
                .setCancelableOutSide(true)
                .setBuildChildListener(new IDialog.OnBuildListener() {
                    @Override
                    public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                        Button btn_take_photo = view.findViewById(R.id.btn_take_photo);
                        btn_take_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "拍照", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        Button btn_select_photo = view.findViewById(R.id.btn_select_photo);
                        btn_select_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "相册选取", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        Button btn_cancel_dialog = view.findViewById(R.id.btn_cancel_dialog);
                        btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "取消", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setScreenWidthP(1.0f)
                .setGravity(Gravity.BOTTOM).show();
    }

    /**
     * 7、分享dialog
     *
     * @param view View
     */
    public void showDialogShare(View view) {
        dialog = new SYDialog.Builder(this)
                .setDialogView(R.layout.layout_share)
                .setWindowBackgroundP(0.5f)
                .setScreenWidthP(1.0f)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(false)
                .setCancelableOutSide(false)
                .setAnimStyle(R.style.AnimUp)
                .setBuildChildListener(new IDialog.OnBuildListener() {
                    @Override
                    public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CommonDialogActivity.this,
                                LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(new ShareAdapter());

                        Button btn_cancel_dialog = view.findViewById(R.id.btn_cancel_dialog);
                        btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "取消", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                }).show();
    }

    /**
     * 8、支持空视图 错误视图 失败重试
     */
    public void showRetryDialog(View view) {
        new SYDialog.Builder(this)
                .setDialogView(R.layout.layout_dialog_retry)
                .setAnimStyle(0)
                .setScreenHeightP(0.7f)
                .setCancelableOutSide(true)
                .setBuildChildListener(new IDialog.OnBuildListener() {
                    @Override
                    public void onBuildChildView(final IDialog dialog, final View parent, int layoutRes) {
                        final DialogLoadingView loadingView = parent.findViewById(R.id.loading_view);
                        //展示加载中
                        loadingView.showLoading();
                        //支持失败重试
                        loadingView.setRetryListener(new DialogLoadingView.OnRetryListener() {
                            @Override
                            public void onRetry() {
                                //模拟成功
                                loadingView.hide();
                            }
                        });

                        ImageView iv_close = parent.findViewById(R.id.iv_close);
                        iv_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        //模拟请求失败
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadingView.showError();
                            }
                        }, 1500);

                    }
                })
                .show();
    }

    /**
     * 9、全局管理dialog
     *
     * @param view View
     */
    public void showGlobalDialog(View view) {

        //Build第一个Dialog
        SYDialog.Builder builder1 = new SYDialog.Builder(this)
                .setDialogView(R.layout.layout_ad_dialog)
                .setWindowBackgroundP(0.5f)
                .setBuildChildListener(new IDialog.OnBuildListener() {
                    @Override
                    public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                        ImageView iv_close = view.findViewById(R.id.iv_close);
                        iv_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        ImageView iv_ad = view.findViewById(R.id.iv_ad);
                        iv_ad.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "点击广告", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                });
        //Build第二个Dialog
        SYDialog.Builder builder2 = new SYDialog.Builder(this)
                .setDialogView(R.layout.layout_bottom_up)
                .setWindowBackgroundP(0.5f)
                .setAnimStyle(R.style.AnimUp)
                .setBuildChildListener(new IDialog.OnBuildListener() {
                    @Override
                    public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                        Button btn_take_photo = view.findViewById(R.id.btn_take_photo);
                        btn_take_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "拍照", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        Button btn_select_photo = view.findViewById(R.id.btn_select_photo);
                        btn_select_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "相册选取", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        Button btn_cancel_dialog = view.findViewById(R.id.btn_cancel_dialog);
                        btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyApplication.getApplication(), "取消", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setScreenWidthP(1.0f)
                .setGravity(Gravity.BOTTOM);
        //添加第一个Dialog
        SYDialogsManager.getInstance().requestShow(new DialogWrapper(builder1));
        //添加第二个Dialog
        SYDialogsManager.getInstance().requestShow(new DialogWrapper(builder2));
    }

    class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareHolder> {

        @Override
        public ShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false);
            return new ShareHolder(view);
        }

        @Override
        public void onBindViewHolder(ShareHolder holder, int position) {
            holder.ll_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyApplication.getApplication(), "分享", Toast.LENGTH_SHORT).show();
                    dismissDialog();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 8;
        }

        class ShareHolder extends RecyclerView.ViewHolder {
            LinearLayout ll_share;

            public ShareHolder(View itemView) {
                super(itemView);
                ll_share = itemView.findViewById(R.id.ll_share);
            }
        }

    }


    /**
     * 关闭弹窗 注意dialog=null;防止内存泄漏
     */
    private void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
