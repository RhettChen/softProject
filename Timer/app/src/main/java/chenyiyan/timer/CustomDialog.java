package chenyiyan.timer;

/**
 * Created by lenovo on 2016/12/18.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
      //  private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        CheckBox typeYear=null;
        CheckBox typeMonth=null;
        CheckBox typeWeek=null;
        CheckBox typeDay=null;
        CheckBox typeLong=null;
        CheckBox level0=null;
        CheckBox level1=null;
        CheckBox level2=null;
        CheckBox level3=null;

        public Builder(Context context) {
            this.context = context;
        }

     /*   public Builder setMessage(String message) {
            this.message = message;
            return this;
        }*/

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        /*public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }*/

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create() {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.dialogTitle)).setText(title);
            // 勾选项目的设置，单选设置
            typeYear=(CheckBox)layout.findViewById(R.id.dialogCheckBox);
            typeMonth=(CheckBox)layout.findViewById(R.id.dialogCheckBox2);
            typeWeek=(CheckBox)layout.findViewById(R.id.dialogCheckBox3);
            typeDay=(CheckBox)layout.findViewById(R.id.dialogCheckBox4);
            typeLong=(CheckBox)layout.findViewById(R.id.dialogCheckBox5);
            level0=(CheckBox)layout.findViewById(R.id.dialogCheckBox6);
            level1=(CheckBox)layout.findViewById(R.id.dialogCheckBox7);
            level2=(CheckBox)layout.findViewById(R.id.dialogCheckBox8);
            level3=(CheckBox)layout.findViewById(R.id.dialogCheckBox9);
            typeYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        typeMonth.setClickable(false);
                        typeWeek.setClickable(false);
                        typeDay.setClickable(false);
                    }else{
                        typeMonth.setClickable(true);
                        typeWeek.setClickable(true);
                        typeDay.setClickable(true);
                    }
                }
            });
            typeMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        typeYear.setClickable(false);
                        typeWeek.setClickable(false);
                        typeDay.setClickable(false);
                    }else{
                        typeYear.setClickable(true);
                        typeWeek.setClickable(true);
                        typeDay.setClickable(true);
                    }
                }
            });

            typeWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        typeMonth.setClickable(false);
                        typeYear.setClickable(false);
                        typeDay.setClickable(false);
                    }else{
                        typeMonth.setClickable(true);
                        typeYear.setClickable(true);
                        typeDay.setClickable(true);
                    }
                }
            });

            typeDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        typeMonth.setClickable(false);
                        typeWeek.setClickable(false);
                        typeYear.setClickable(false);
                    }else{
                        typeMonth.setClickable(true);
                        typeWeek.setClickable(true);
                        typeYear.setClickable(true);
                    }
                }
            });

            level0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        level1.setClickable(false);
                        level2.setClickable(false);
                        level3.setClickable(false);
                    }else{
                        level1.setClickable(true);
                        level2.setClickable(true);
                        level3.setClickable(true);
                    }
                }
            });

            level1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        level0.setClickable(false);
                        level2.setClickable(false);
                        level3.setClickable(false);
                    }else{
                        level0.setClickable(true);
                        level2.setClickable(true);
                        level3.setClickable(true);
                    }
                }
            });

            level2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        level1.setClickable(false);
                        level0.setClickable(false);
                        level3.setClickable(false);
                    }else{
                        level1.setClickable(true);
                        level0.setClickable(true);
                        level3.setClickable(true);
                    }
                }
            });

            level3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        level1.setClickable(false);
                        level2.setClickable(false);
                        level0.setClickable(false);
                    }else{
                        level1.setClickable(true);
                        level2.setClickable(true);
                        level0.setClickable(true);
                    }
                }
            });
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.dialogPositiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialogPositiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialogPositiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.dialogNegativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialogNegativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialogNegativeButton).setVisibility(
                        View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}