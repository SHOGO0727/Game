import java.util.*;

public class BlackjuckA{

    static int money = 0;
    static int bet = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

      System.out.print("軍資金=\\");
      money = nextIntE();

while(true){

      //カードを用意
      int[][] card = new int[4][13];

      //点数を代入
      for(int i=0;i<4;i++){
        for(int j=0;j<13;j++){
          if(j>=9){
            card[i][j] = 10;
          }
          else if(j==0){
            card[i][j] = 11;
          }
          else{
            card[i][j] = j+1;
          }
        }
      }

      //手札の格納(最大n枚)
      int n = 100000;
      int[] dealer = new int[n];
      int[] player = new int[n];

      //カード選択の変数
      int select4; //マークの選択
      int select13; //数字の選択

      //掛け金を入力してください。
      while(true){
        System.out.print("掛け金=\\");
        bet = nextIntE();
        if(money>=bet){
          break;
        }
        else{
          System.out.println("お金が足りません。");
        }
      }

      //ディーラの初期手札決定
      int cntd=0; //ディーラが引いた枚数
      while(true){
        dealer[cntd] = drawCard(card);
        cntd++;
        if(cntd==2){break;}
      }
      //合計値格納
      int sumd = dealer[0]+dealer[1];

      //プレイヤーの初期手札決定
      int cntp=0; //プレイヤーが引いた枚数
      while(true){
        player[cntp] = drawCard(card);
        cntp++;
        if(cntp==2){break;} //初期手札は2枚
      }
      //合計値格納
      int sump = player[0]+player[1];

      System.out.print("dealerの手札：");
      System.out.println(dealer[0]+" ?");

      playerHandPrint(cntp,player,sump);

      while(true){

        //エース判定
        for(int i=0;i<cntp;i++){
          if(player[i]==11){
            String ace;
            while(true){
              System.out.print("エースを1にしますか？（y,n）：");
              ace = sc.next();
              if(ace.equals("y") || ace.equals("n")){break;}
            }
            if(ace.equals("y")){
              player[i]=1;
              sump -= 10;
              playerHandPrint(cntp,player,sump);
            }
          }
        }

        //バースト判定
        if(sump>21){
          System.out.println("手札の合計が21を超えました。");
          break;
        }

        //プレイヤーの行動
        String move;

        while(true){
          System.out.print("次の行動を入力してください。（ヒット：h, スタンド:s, ダブルダウン:d）：");
          move = sc.next();
          if(move.equals("d") && money<bet*2){
            System.out.println("お金が足りません。");
          }
          else if(move.equals("s") || move.equals("h") || move.equals("d")){break;}
        }
        if(move.equals("s")){break;}
        if(move.equals("h") || move.equals("d")){
          player[cntp] = drawCard(card);
          sump += player[cntp];
          cntp++;
          if(move.equals("d")){
            bet *= 2;
            break;
          }
        }
        playerHandPrint(cntp,player,sump);

      }
      //プレイヤーのターン終了

      //dealerは17以上になるまで追加
      dealerHandPrint(cntd,dealer,sumd);
      while(sumd<17){
        dealer[cntd] = drawCard(card);
        sumd += dealer[cntd];
        cntd++;
        dealerHandPrint(cntd,dealer,sumd);
      }

      System.out.println("-------------最終結果--------------");
      dealerHandPrint(cntd,dealer,sumd);
      playerHandPrint(cntp,player,sump);
      System.out.println("-----------------------------------");

      //勝敗判定
      judge(sump,cntp,sumd,cntd);


      System.out.println("所持金："+money);
      if(money<=0){
        System.out.println("軍資金がありません。");
        break;
      }
      System.out.print("cで終了：");
      if((sc.next()).equals("c")){
        System.out.println("終了します。");
        break;
      }

} //whileの終わり

    } //mainの終わり

    //以降メソッド

    static void playerHandPrint(int cntp,int[] player,int sump){
      System.out.print("playerの手札：");
      for(int i=0;i<cntp;i++){
        if(player[i]!=0){
          System.out.print(player[i]+" ");
        }
      }
      System.out.println("（合計："+sump+"）");
    }

    static void dealerHandPrint(int cntd,int[] dealer,int sumd){
      System.out.print("delaerの手札：");
      for(int i=0;i<cntd;i++){
        if(dealer[i]!=0){
          System.out.print(dealer[i]+" ");
        }
      }
      System.out.println("（合計："+sumd+"）");
    }

    static int drawCard(int[][] card){
      while(true){
        int select4 = (int)((Math.random()*4));
        int select13 = (int)((Math.random()*13));
        if(card[select4][select13]!=0){
          int tmp = card[select4][select13];
          card[select4][select13] = 0;
          return tmp;
        }
      }
    }

    static int nextIntE(){
      while(true){
        String input = sc.next();
        try{
          int in = Integer.parseInt(input);
          return in;
        }
        catch(Exception e){
          System.out.println("正しく入力してください。");
        }
      }
    }

    static void judge(int sump,int cntp,int sumd,int cntd){

      System.out.print("判定：");
      //勝敗判定
      if(sump>21){ //バーストしたら負け。
        System.out.println("負け");
        money -= bet;
      }
      else if(sumd==21 && cntd==2){  //ブラックジャック同士はディーラが勝つらしい。
        System.out.println("負け");
        money -= bet;
      }
      else if(sump==21 && cntp==2){
        System.out.println("勝ち");
        money += bet;
      }
      else{ //バーストしてない。
        if(sumd>21){
          System.out.println("勝ち");
          money += bet;
        }
        else{ //ディーラもバーストしてない。
          if(sump>sumd){
            System.out.println("勝ち");
            money += bet;
          }
          else if(sump==sumd){
            System.out.println("引き分け");
          }
          else{
            System.out.println("負け");
            money -= bet;
          }
        }
      }
    }

}
