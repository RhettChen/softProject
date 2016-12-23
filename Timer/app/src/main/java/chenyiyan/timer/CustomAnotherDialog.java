package chenyiyan.timer;

/**
 * Created by lenovo on 2016/12/21.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CustomAnotherDialog extends Dialog {

    public CustomAnotherDialog(Context context) {
        super(context);
    }

    public CustomAnotherDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private taskEntry target;
        //  private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private String neutralButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private DialogInterface.OnClickListener neutralButtonClickListener;

        public Builder(Context context,taskEntry tg) {
            this.context = context;
            this.target = tg;
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

        public taskEntry getTask(){
            return target;
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

        public Builder setNeutralButton(int neutralButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.neutralButtonText = (String) context
                    .getText(neutralButtonText);
            this.neutralButtonClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(String neutralButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.neutralButtonText = neutralButtonText;
            this.neutralButtonClickListener = listener;
            return this;
        }

        public CustomAnotherDialog create() {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomAnotherDialog dialog = new CustomAnotherDialog(context);
            View layout = inflater.inflate(R.layout.dialog_another, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.dialogAnotherTitle)).setText(title);

            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.dialogAnotherPositiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialogAnotherPositiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialogAnotherPositiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.dialogAnotherNegativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialogAnotherNegativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialogAnotherNegativeButton).setVisibility(
                        View.GONE);
            }

            // set the neutral button
            if (neutralButtonText != null) {
                ((Button) layout.findViewById(R.id.dialogAnotherNeutralButton))
                        .setText(neutralButtonText);
                if (neutralButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialogAnotherNeutralButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    neutralButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEUTRAL);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialogAnotherNeutralButton).setVisibility(
                        View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}