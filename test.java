import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;


public class test extends JFrame  implements ActionListener{
	JPanel panel = new JPanel();
	JLabel back = new JLabel();
	JLabel comment = new JLabel();
	JLabel[] label = new JLabel[350];
	JLabel[] student = new JLabel[50];
	JRadioButton[] radio = new JRadioButton[50];
	JRadioButton[] color = new JRadioButton[6];
	ButtonGroup group = new ButtonGroup();
	ButtonGroup group2 = new ButtonGroup();
	public int YOKO= 30;  //内装を表すセル１つの横幅  変更するときはframeのsetBounds内の数値も変更
	public int TATE= 30;  //内装を表すセル１つの縦幅                   〃
	public int GYO= 15;   //教室の横のセル数（行数）                   〃
	public int RETU= 20;  //教室の縦のセル数（列数）                   〃
//	int flag = 0;         //決定しないと次のテーブルを出せないようにするもの
	int flag2 = 0;        //どのラジオボタンがチェックされているか判断するもの
	int cell[] = new int[350];//セルに机があるかどうかを保存する。番号の振り方はプログラムの下に示す
	int pos[]= new int[50];//机の左上の位置を保存する。番号の振り方はプログラムの下に示す
	int muki[] = new int[50];//机の向き情報。0なら横向き1なら縦向き
	int color2[] = new int[50];//生徒ごとの色
	int count = 0;
	String[] strs = new String[50];
	JTextField name = new JTextField();
    JButton make = new JButton();
	JButton TATEtable = new JButton();
	JButton YOKOtable = new JButton();
	JButton delete = new JButton();
	JButton L = new JButton();
	JButton R = new JButton();
	JButton U = new JButton();
	JButton D = new JButton();
	JButton print = new JButton();
	JButton input = new JButton();
	JButton output = new JButton();

//	JButton END = new JButton();



	  public static void main(String[] args){
		    test frame = new test();

		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setExtendedState(Frame.MAXIMIZED_BOTH);//フルスクリーンに
		    frame.setTitle("座席表作成プログラム");
		    frame.setVisible(true);
		  }

	  test(){
		panel = new JPanel();
	    panel.setBackground(Color.WHITE);
	    panel.setLayout(null);


		  for(int i = 0;i<50;i++){
			  radio[i] = new JRadioButton();
			  radio[i].addActionListener(this);
			  student[i] = new JLabel();
			  student[i].setOpaque(true);
			  	panel.add(student[i]);
				panel.add(radio[i]);
			  }

		  for(int i = 0;i<6;i++){
			  color[i] = new JRadioButton();
			  color[i].addActionListener(new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent e) {
					    color_actionPerformed(e);
					}
				  });
			  panel.add(color[i]);
		  }
			color[0].setSelected(true);
			color[0].setText("赤");
			color[0].setBounds(GYO*YOKO+370,25,100,20);
			group2.add(color[0]);
			color[1].setText("青");
			color[1].setBounds(GYO*YOKO+370,45,100,20);
			group2.add(color[1]);
			color[2].setText("黄");
			color[2].setBounds(GYO*YOKO+370,65,100,20);
			group2.add(color[2]);
			color[3].setText("緑");
			color[3].setBounds(GYO*YOKO+370,85,100,20);
			group2.add(color[3]);
			color[4].setText("紫");
			color[4].setBounds(GYO*YOKO+370,105,100,20);
			group2.add(color[4]);
			color[5].setText("橙");
			color[5].setBounds(GYO*YOKO+370,125,100,20);
			group2.add(color[5]);




		  for(int i=0;i<GYO;i++){
			  for(int i2=0; i2<RETU; i2++){
				  label[i+i2] = new JLabel();
				  label[i+i2].setOpaque(true);
				  label[i+i2].setBackground(Color.WHITE);
				  label[i+i2].setBounds(new Rectangle(i*YOKO + i, i2*TATE + i2, YOKO, TATE));
				  	panel.add(label[i+i2]);
			  }
		  }

		  back = new JLabel();
		  back.setBackground(Color.BLACK);
		  back.setBounds(new Rectangle(0, 0, YOKO*GYO+GYO, TATE*RETU+RETU));
		  back.setOpaque(true);

                   /*             ここまでで背景作成                */
		  comment = new JLabel("コメントが表示されます");
		 // comment.setBackground(Color.BLACK);
		  comment.setBounds(new Rectangle(GYO*YOKO+50,(RETU*TATE)-30, 500, 50));
		  comment.setOpaque(true);
		  panel.add(comment,null);
          /*学生の名前を入力するトコ*/
		  name.setColumns(10);
		  name.setBounds(new Rectangle((GYO*YOKO) + 50, 20,150,25));

          /*テーブルを作成するボタン*/
		  make.setLabel("テーブル作成");
		  make.setBounds(new Rectangle((GYO*YOKO) + 50, 60, 120, 25));
		  make.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e) {
			    make_actionPerformed(e);
			}
		  });




		  TATEtable.setLabel("縦向きに変更");
		  TATEtable.setBounds(new Rectangle((GYO*YOKO) + 50, 110, 120, 25));
		  TATEtable.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e) {
			    TATEtable_actionPerformed(e);
			}
		  });

		  YOKOtable.setLabel("横向きに変更");
		  YOKOtable.setBounds(new Rectangle((GYO*YOKO) + 50, 160, 120, 25));
		  YOKOtable.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent e) {
				    YOKOtable_actionPerformed(e);
			}
		  });


		  delete.setLabel("削除");
		  delete.setBounds(new Rectangle(GYO*YOKO+50,(RETU*TATE)-60,100, 30));
		  delete.addActionListener(new java.awt.event.ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		delete_actionPerformed(e);
		    		}
		    	});


		    R.setLabel("→");
		    R.setBounds(new Rectangle(GYO*YOKO+150, 230, 50, 30));
		    R.addActionListener(new java.awt.event.ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		R_actionPerformed(e);
		    		}
		    	});

		    L.setLabel("←");
		    L.setBounds(new Rectangle(GYO*YOKO+50, 230, 50, 30));
		    L.addActionListener(new java.awt.event.ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		L_actionPerformed(e);
		    		}
		    	});

		    U.setLabel("↑");
		    U.setBounds(new Rectangle(GYO*YOKO+100, 200, 50, 30));
		    U.addActionListener(new java.awt.event.ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		U_actionPerformed(e);
		    		}
		    	});

		    D.setLabel("↓");
		    D.setBounds(new Rectangle(GYO*YOKO+100, 260, 50, 30));
		    D.addActionListener(new java.awt.event.ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		D_actionPerformed(e);
		    		}
		    	});

		    print.setLabel("印刷");
			print.setBounds(new Rectangle(GYO*YOKO+160,(RETU*TATE)-60,100, 30));
			print.addActionListener(new java.awt.event.ActionListener() {
			  	public void actionPerformed(ActionEvent e) {
			  		print_actionPerformed(e);
			  		}
			  	});


		    input.setLabel("読み込み");
			input.setBounds(new Rectangle(GYO*YOKO+160,(RETU*TATE)-110,100, 30));
			input.addActionListener(new java.awt.event.ActionListener() {
			  	public void actionPerformed(ActionEvent e) {
			  		input_actionPerformed(e);
			  		}

			  	});
			output.setLabel("保存");
			output.setBounds(new Rectangle(GYO*YOKO+50,(RETU*TATE)-110,100, 30));
			output.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					output_actionPerformed(e);
					}
				});

			panel.add(back);
			panel.add(name);
			panel.add(make);
			panel.add(TATEtable);
			panel.add(YOKOtable);
			panel.add(delete);
		    panel.add(L);
		    panel.add(R);
		    panel.add(D);
		    panel.add(U);
		    panel.add(print);
		    panel.add(input);
		    panel.add(output);
//		    panel.add(END);




		    getContentPane().add(panel, BorderLayout.CENTER);


	  }
      /*テーブル作成*/
		private void make_actionPerformed(ActionEvent e) {
			String str = name.getText();//入力されたテキストを習得
			for(int i=0;i<count+1;i++){
				String str2 =student[i].getText();
				if(str2.equals(str)){
					comment.setText(str+"はすでにあります");
					return;
				}
			}

			if(true==color[0].isSelected()){
				student[count].setBackground(Color.RED);
			}
			if(true==color[1].isSelected()){
				student[count].setBackground(Color.BLUE);
			}
			if(true==color[2].isSelected()){
				student[count].setBackground(Color.YELLOW);
			}
			if(true==color[3].isSelected()){
				student[count].setBackground(Color.GREEN);
			}
			if(true==color[4].isSelected()){
				student[count].setBackground(Color.MAGENTA);
			}
			if(true==color[5].isSelected()){
				student[count].setBackground(Color.ORANGE);
			}
			student[count].setText(str);//テーブルに名前をはる
			student[count].setHorizontalAlignment(JLabel.CENTER);
			student[count].setBounds(new Rectangle(0, 0, YOKO*3+2, TATE*2+1));
			radio[count].setText(str);
			radio[count].setBounds(GYO*YOKO+250,25+count*20,100,20);
			group.add(radio[count]);
			radio[count].setSelected(true);//新たに作ったラジオボタンをチェック
			flag2=count;
			pos[count]=1;//x=0,y=0に造るから
			cell[1]++;cell[2]++;cell[1+RETU]++;cell[2+RETU]++;cell[1+2*RETU]++;cell[2+2*RETU]++;//机のあるセルに1を与える
			count++;
			comment.setText(str+"を作りました");
		}

	  /*縦向きに変更*/
		private void TATEtable_actionPerformed(ActionEvent e) {
			int i=0;
			if(muki[flag2]==0){
					if(pos[flag2]%RETU==RETU-1){//pos[flag2]が下から2番目のセルだったら、
				i=1;
			}
			int x = student[flag2].getLocation().x;
			int y = student[flag2].getLocation().y-TATE*i-i;
			student[flag2].setBounds(new Rectangle(x, y, YOKO*2+1, TATE*3+2));
			cell[pos[flag2]+2*RETU]--;cell[pos[flag2]+1+2*RETU]--;//横向きだと触れていて、縦向きだと触れていないセルに0を与える
			cell[pos[flag2]+2-3*i]++;cell[pos[flag2]+2+RETU-3*i]++;//縦向きだと触れていて、横向きだと触れていないセルに1を与える
			muki[flag2] = 1;
			pos[flag2] = pos[flag2] - i;
			}
		}
		/*横向きに変更*/
		private void YOKOtable_actionPerformed(ActionEvent e) {
			int i=0;
			if(muki[flag2]==1){
					if(pos[flag2] > (GYO-2)*RETU){//pos[flag2]が右から二番目のセルだったら
				i=1;
			}
			int x = student[flag2].getLocation().x-i*YOKO-i;
			int y = student[flag2].getLocation().y;
			student[flag2].setBounds(new Rectangle(x, y, YOKO*3+2, TATE*2+1));
			cell[pos[flag2]+2*RETU-3*i*RETU]++;cell[pos[flag2]+1+2*RETU-3*i*RETU]++;//縦向きだと触れていなくて、横向きだと触れているセルに1を与える
			cell[pos[flag2]+2]--;cell[pos[flag2]+2+RETU]--;//縦向きだと触れていて、横向きだと触れていないセルに0を与える
			muki[flag2]=0;
			pos[flag2] = pos[flag2] - i*RETU;
			}
		}
		/*右に移動*/
		private void R_actionPerformed(ActionEvent e) {
			if(muki[flag2]==0 && pos[flag2] <= (GYO-3)*RETU || muki[flag2]==1 && pos[flag2] <= (GYO-2)*RETU){//右端じゃない時
				if(muki[flag2]==0&&cell[pos[flag2]+3*RETU]+cell[pos[flag2]+3*RETU+1]==0||//右のセルにすでにテーブルがないか
						muki[flag2]==1&&cell[pos[flag2]+2*RETU]+cell[pos[flag2]+2*RETU+1]+cell[pos[flag2]+2*RETU+2]==0){//同上

					if(muki[flag2]==0&&cell[pos[flag2]+RETU]+cell[pos[flag2]+RETU+1]+cell[pos[flag2]+2*RETU]+cell[pos[flag2]+2*RETU+1]==4||//異動後に重なりはないか
							muki[flag2]==1&&cell[pos[flag2]+RETU]+cell[pos[flag2]+RETU+1]+cell[pos[flag2]+RETU+2]==3){//同上
						comment.setText("");
					}
					else {comment.setText("重なってますよ");}
				}
				else {comment.setText("重なってますよ");}
					if(muki[flag2]==0){//横の時
						cell[pos[flag2]]--;cell[pos[flag2]+1]--;//移動前にあって、異動後に亡くなるセルを－１
						cell[pos[flag2]+3*RETU]++;cell[pos[flag2]+3*RETU+1]++;//異動前に無くて、異動後にあるセルを＋１
					}
					else{//縦の時
						cell[pos[flag2]]--;cell[pos[flag2]+1]--;cell[pos[flag2]+2]--;;
						cell[pos[flag2]+2*RETU]++;cell[pos[flag2]+2*RETU+1]++;cell[pos[flag2]+2*RETU+2]++;
					}
			pos[flag2]=pos[flag2]+RETU;//右に行くので位置がGYOだけずれる
			int x = student[flag2].getLocation().x+YOKO+1;
			int y = student[flag2].getLocation().y;
			Point p = new Point(x,y);
			student[flag2].setLocation(p);

			}
			else {comment.setText("それ以上右にはいけません！！");}
		}

		/*左に移動*/
		private void L_actionPerformed(ActionEvent e) {
			if(pos[flag2]>RETU){//左端の時
				if(muki[flag2]==0 && cell[pos[flag2]-RETU]+cell[pos[flag2]-RETU+1] == 0||
						muki[flag2]==1 && cell[pos[flag2]-RETU]+cell[pos[flag2]-RETU+1]+cell[pos[flag2]-RETU+2] == 0){
					if(muki[flag2]==0&&cell[pos[flag2]]+cell[pos[flag2]+1]+cell[pos[flag2]+RETU]+cell[pos[flag2]+RETU+1]==4||
							muki[flag2]==1&&cell[pos[flag2]]+cell[pos[flag2]+1]+cell[pos[flag2]+2]==3){
						comment.setText("");
					}
					else {comment.setText("重なってますよ");}
				}
				else {comment.setText("重なってますよ");}
					if(muki[flag2]==0){//横の時
						cell[pos[flag2]+2*RETU]--;cell[pos[flag2]+2*RETU+1]--;
						cell[pos[flag2]-RETU]++;cell[pos[flag2]-RETU+1]++;
					}
					else{//縦の時
						cell[pos[flag2]+RETU]--;cell[pos[flag2]+RETU+1]--;cell[pos[flag2]+RETU+2]--;
						cell[pos[flag2]-RETU]++;cell[pos[flag2]-RETU+1]++;cell[pos[flag2]-RETU+2]++;
					}
					pos[flag2]=pos[flag2]-RETU;//左に行くので位置がGYOだけずれる
			int x = student[flag2].getLocation().x-YOKO-1;
			int y = student[flag2].getLocation().y;
			Point p = new Point(x,y);
			student[flag2].setLocation(p);


			}
			else {comment.setText("それ以上左にはいけません！！");}
			}

		/*上に移動*/
		private void U_actionPerformed(ActionEvent e) {
			if(pos[flag2]%RETU!=1){//一番上の時
				if(muki[flag2]==0&&cell[pos[flag2]-1]+cell[pos[flag2]-1+RETU]+cell[pos[flag2]-1+2*RETU]==0||
						muki[flag2]==1&&cell[pos[flag2]-1]+cell[pos[flag2]-1+RETU]==0){
					if(muki[flag2]==0&&cell[pos[flag2]]+cell[pos[flag2]+RETU]+cell[pos[flag2]+2*RETU]==3||
							muki[flag2]==1&&cell[pos[flag2]]+cell[pos[flag2]+RETU]+cell[pos[flag2]+1]+cell[pos[flag2]+RETU+1]==4){
						comment.setText("");
					}
					else {comment.setText("重なってますよ");}
					}
				else {comment.setText("重なってますよ");}
					if(muki[flag2]==0){//横向き
						cell[pos[flag2]+1]--;cell[pos[flag2]+RETU+1]--;cell[pos[flag2]+2*RETU+1]--;;
						cell[pos[flag2]-1]++;cell[pos[flag2]+RETU-1]++;cell[pos[flag2]+2*RETU-1]++;
					}
					else{//縦向き
						cell[pos[flag2]+2]--;cell[pos[flag2]+RETU+2]--;
						cell[pos[flag2]-1]++;cell[pos[flag2]+RETU-1]++;
					}
			pos[flag2]--;
			int x = student[flag2].getLocation().x;
			int y = student[flag2].getLocation().y-TATE-1;
			Point p = new Point(x,y);
			student[flag2].setLocation(p);

			}
			else {comment.setText("それ以上上にはいけません!！");}
			}

		/*下に移動*/
		private void D_actionPerformed(ActionEvent e) {
			if(muki[flag2]==0&&pos[flag2]%RETU!=RETU-1 || muki[flag2] ==1 && pos[flag2]%RETU!=RETU-2){//一番下じゃない時
				if(muki[flag2]==0 && cell[pos[flag2]+2]+cell[pos[flag2]+2+RETU]+cell[pos[flag2]+2+2*RETU]==0||
						muki[flag2]==1 && cell[pos[flag2]+3]+cell[pos[flag2]+3+RETU]==0){//下に行ったときに触れるセルに他の机がないか縦の場合と横の場合に分けて判定
					if(muki[flag2]==0&&cell[pos[flag2]+1]+cell[pos[flag2]+1+RETU]+cell[pos[flag2]+1+2*RETU]==3||
							muki[flag2]==1&&cell[pos[flag2]+2]+cell[pos[flag2]+2+RETU]+cell[pos[flag2]+1]+cell[pos[flag2]+1+RETU]==4){
						comment.setText("");
					}
					else {comment.setText("重なってますよ");}
					}
				else {comment.setText("重なってますよ");}
				if(muki[flag2] == 0){//横向きなら
					cell[pos[flag2]]--;cell[pos[flag2]+RETU]--;cell[pos[flag2]+2*RETU]--;//移動前に机があって、移動後になくなるセルに０を与える
					cell[pos[flag2]+2]++;cell[pos[flag2]+RETU+2]++;cell[pos[flag2]+2*RETU+2]++;//移動前に机がなくて、移動後にあるセルに１を与える
					            }
				else{//縦向きなら
					cell[pos[flag2]]--;cell[pos[flag2]+RETU]--;
					cell[pos[flag2]+3]++;cell[pos[flag2]+RETU+3]++;
					}
				int x = student[flag2].getLocation().x;
				int y = student[flag2].getLocation().y+(TATE+1);
				Point p = new Point(x,y);
				student[flag2].setLocation(p);
				pos[flag2]++;
				}
			else {comment.setText("それ以上下にはいけません！！");}
			}
		/*操作中のテーブルを固定*/
		/*
				private void END_actionPerformed(ActionEvent e) {
			if(flag==1){
				count++;
				flag=0;
			}
		}*/
		//生徒のラジオボタンを押したときの動作
		public void actionPerformed(ActionEvent e) {
			flag2=0;
			for(int i =0;flag2==0;i++){
				if(true == radio[i].isSelected()){
					flag2=i;
					}
				}
			color[color2[flag2]].setSelected(true);
		}

		//カラーのラジオボタンを押したときの動作
		private void color_actionPerformed(ActionEvent e) {
			   if(true==color[0].isSelected()){
				   student[flag2].setBackground(Color.RED);
				   color2[flag2] = 0;
			   }
			   if(true==color[1].isSelected()){
				   student[flag2].setBackground(Color.BLUE);
			       color2[flag2] = 1;
			   }
			   if(true==color[2].isSelected()){
				   student[flag2].setBackground(Color.YELLOW);
				   color2[flag2] = 2;
			   }
			   if(true==color[3].isSelected()){
				   student[flag2].setBackground(Color.GREEN);
				   color2[flag2] = 3;
			   }
			   if(true==color[4].isSelected()){
				   student[flag2].setBackground(Color.MAGENTA);
				   color2[flag2] = 4;
			   }
			   if(true==color[5].isSelected()){
				   student[flag2].setBackground(Color.ORANGE);
				   color2[flag2] = 5;
			   }
		}

		/*削除ボタンを押したときの動作*/
		private void delete_actionPerformed(ActionEvent e) {
			student[flag2].setBounds(new Rectangle(0, 0, 0,0));
			if(muki[flag2]==0){
				cell[pos[flag2]]--;cell[pos[flag2]+1]--;cell[pos[flag2]+RETU]--;cell[pos[flag2]+RETU+1]--;cell[pos[flag2]+2*RETU]--;cell[pos[flag2]+2*RETU+1]--;
			}
			else{
				cell[pos[flag2]]--;cell[pos[flag2]+1]--;cell[pos[flag2]+2]--;cell[pos[flag2]+RETU]--;cell[pos[flag2]+RETU+1]--;cell[pos[flag2]+RETU+2]--;
			}
			if(flag2 != 0){//一番上の生徒が選ばれていない時
				radio[flag2-1].setSelected(true);//ひとつ手前のラジオボタンをチェック
					for(int i=0;i+flag2<48;i++){//空白を詰める
						pos[flag2+i]=pos[flag2+i+1];
						student[flag2+i]=student[flag2+i+1];
						muki[flag2+i]=muki[flag2+i+1];
						String str = radio[flag2+i+1].getText();
						radio[flag2+i].setText(str);
						if(student[i]==student[count])break;
						}
					radio[count-1].setBounds(0,0,0,0);
					flag2--;
			}

			else{
					for(int i=0;i+flag2<=48;i++){//空白を詰める
						pos[i]=pos[i+1];
						System.out.println(i);
						student[i]=student[i+1];
						muki[i]=muki[i+1];
						String str = radio[i+1].getText();
						radio[i].setText(str);
					//	if(student[i]==student[count])break;
					}
					radio[count-1].setBounds(0,0,0,0);
			}
			comment.setText("削除しました");

				count--;
			}
//印刷ボタンを押したときの動作
		private void print_actionPerformed(ActionEvent e) {
	        PrintJob pjob;
	        Graphics pg;
	        for(int i=0;i<count;i++){
	        	if(cell[pos[i]]>=2){//重なっている時
	    			comment.setText("重なっている部分が無い状態にしてください");
	    			return;
	        	}
	        }
//	        back.setBackground(Color.WHITE);
			  back.setBounds(new Rectangle(0, 0, 0, 0));

	           pjob = getToolkit().getPrintJob(this, "Printing Test", null);
	            if (pjob != null) {
	                 // プリンタに送るイメージを取得する
	                pg = pjob.getGraphics();
	                if (pg != null) {
	                         //このコンポーネントとサブコンポーネントを印刷する
	                        this.printAll(pg);
	                        // 印刷に使用したグラフィックスを消去する
	                        pg.dispose();
	                }
	                pjob.end();
	            }
	  		  back.setBounds(new Rectangle(0, 0, YOKO*GYO+GYO, TATE*RETU+RETU));
		}
//インプットを押したときのどうさ
		private void input_actionPerformed(ActionEvent e) {
			FileInputStream stream = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			File file = new File("zaseki.txt");
			try {
			  stream = new FileInputStream(file);
			  isr = new InputStreamReader(stream);
			  br = new BufferedReader(isr);

			  int c = 0;
			  String read;

			  while ((read = br.readLine()) != null) {  // 読み込み
			    strs[c] = read;
			    c++;
			  }

			} catch (IOException x) {
			  x.printStackTrace();
			} finally {
			  try {
			    if (br != null) {
			      br.close();
			    }
			  } catch (IOException x) {
			    x.printStackTrace();
			  }
			}
//ここから全体を再構築する
			count = Integer.parseInt(strs[0]);
			/* flag2=1 ;*/flag2=count-1;//がただしい
			for(int i = 0;i<50;i++){
				student[i].setBounds(new Rectangle(0, 0, 0,0));
				radio[i].setBounds(0,0,0,0);
				pos[i]=0;
				muki[i]=0;
			//	radio[flag2+i].setText("");
				}
			for(int i =0;i<GYO*RETU;i++){
				cell[i]=0;
			}
			for(int i= 0;i<count;i++){
				student[i].setText(strs[i*4+1]);
				student[i].setHorizontalAlignment(JLabel.CENTER);
				radio[i].setText(strs[i*4+1]);
				group.add(radio[i]);
				radio[i].setSelected(true);
				color2[i] = Integer.parseInt(strs[i*4+2]);
				muki[i] = Integer.parseInt(strs[i*4+3]);
				pos[i] = Integer.parseInt(strs[i*4+4]);
				student[i].setBounds(new Rectangle((YOKO+1)*(pos[i]/RETU), (TATE+1)*(pos[i]%RETU-1), YOKO*3+2, TATE*2+1));
				if(muki[i]==0){
					cell[pos[i]]++;cell[pos[i]+1]++;cell[pos[i]+RETU]++;cell[pos[i]+1+RETU]++;cell[pos[i]+2*RETU]++;cell[pos[i]+1+2*RETU]++;
				}
				else{
					cell[pos[i]]++;cell[pos[i]+1]++;cell[pos[i]+2]++;cell[pos[i]+RETU]++;cell[pos[i]+1+RETU]++;cell[pos[i]+2+RETU]++;
				}
				radio[i].setBounds(GYO*YOKO+250,25+i*20,100,20);



				if(color2[i]==0){
					student[i].setBackground(Color.RED);
				}
				if(color2[i]==1){
					student[i].setBackground(Color.BLUE);
				}
				if(color2[i]==2){
					student[i].setBackground(Color.YELLOW);
				}
				if(color2[i]==3){
					student[i].setBackground(Color.GREEN);
				}
				if(color2[i]==4){
					student[i].setBackground(Color.MAGENTA);
				}
				if(color2[i]==5){
					student[i].setBackground(Color.ORANGE);
				}
			}


		}
//アウトプットを押したときの動作
		private void output_actionPerformed(ActionEvent e){
		FileOutputStream stream = null;
		OutputStreamWriter osr = null;
		BufferedWriter bw = null;

			File file = new File("zaseki.txt");
			try {
			  stream = new FileOutputStream(file);
			  osr = new OutputStreamWriter(stream);
			  bw = new BufferedWriter(osr);
			  bw.write(Integer.toString(count), 0, Integer.toString(count).length());
			  bw.newLine();
			  for (int i = 0; i < count; i++) {
			    // 書き込み
			    bw.write(student[i].getText(), 0, student[i].getText().length());
			    bw.newLine();
			    bw.write(Integer.toString(color2[i]), 0, Integer.toString(color2[i]).length());
			    bw.newLine();
			    bw.write(Integer.toString(muki[i]), 0, Integer.toString(muki[i]).length());
			    bw.newLine();
			    bw.write(Integer.toString(pos[i]), 0, Integer.toString(pos[i]).length());
			    bw.newLine();

			  }
			} catch (IOException i) {
			  i.printStackTrace();
			} finally {
			  try {
			    if (bw != null) {
			      bw.close();
			    }
			  } catch (IOException i) {
			    i.printStackTrace();
			  }
			}

		}

}


/*　　　　名前を入力し、横テーブル作成、縦テーブル作成ボタンを押すことでオブジェクトを生成
          方向キーボタンを作ってそれを操作してテーブル（横テーブルの場合、縦２横３）のオブジェクトを移動
 　　　　　決定ボタンでそこに固定
 　　　　　ENDとテーブル作成にフラグをつくって、何回押しても無駄にcountが進まないようにする
           アクションがあってからpanelにaddすると処理が遅くなるので、あらかじめpanelにaddしておく


           生徒名を押したときにカラーの色も対応したチェックボックスがチェックされる。



チェックボックスが押されたときの動作をして適当な変数にどのボックスが押されたかを保存して
操作の部分でその変数を読み取ってどのラベルを動かすかを判断する。


flag3はセルに対して下のような法則で数字を与えたときに、机の左上のセルの番号をflag3とする。だから机をつくったとき、flag3は１にする。下に行くと２、右に行くと６、みぎにいって下に行くと７になる。
 1  6 11 16
 2  7 12 17
 3  8 13 18
 4  9 14 19
 5 10 15 20
  */


