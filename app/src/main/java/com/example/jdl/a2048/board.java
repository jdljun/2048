package com.example.jdl.a2048;

/**
 * Created by jdl on 2017/12/11.
 */


class block {
    private int value;

    public boolean iscomputed;

    public block(){
        value=0;
    }

    public block(int value){
        this.value=value;
    }

    public void changevalue(int value) {
        this.value=value;
    }

    public int getvalue() {
        return value;
    }

}
public class board {
    private block[] b=new block[16];
    private boolean  ischange;
    public boolean notchange=false;

    board(){
        for(int i=0;i<16;i++)
            b[i]=new block();
        randomone();
        randomone();
        //print_board();
    }

    public void randomone() {

        int position=(int)(Math.random()*16);
        if(b[position].getvalue()==0) {
            int num=(int)(Math.random()*100);
            if(num<=5)
                b[position].changevalue(4);
            else
                b[position].changevalue(2);
        }
        else
            randomone();

    }

    public void right() {
        left_rotation(get_mux_b());
        up();
        left_rotation(get_mux_b());
        left_rotation(get_mux_b());
        left_rotation(get_mux_b());
    }



    public void left() {
        left_rotation(get_mux_b());
        left_rotation(get_mux_b());
        left_rotation(get_mux_b());
        up();
        left_rotation(get_mux_b());
    }

    public void down() {
        left_rotation(get_mux_b());
        left_rotation(get_mux_b());
        up();
        left_rotation(get_mux_b());
        left_rotation(get_mux_b());
    }

    public void up() {
        int position;

        ischange=false;
        for(int i=0;i<16;i++) {
            b[i].iscomputed=false;
            position=i;
            while(  position-4>=0 && b[position].getvalue()>0 ) {
                if((b[position-4].getvalue()!=0 && b[position-4].getvalue()!=b[position].getvalue()) ||b[position-4].iscomputed==true)
                    break;
                if(b[position-4].getvalue()>0&&b[position-4].getvalue()==b[position].getvalue()&&b[position].iscomputed==false) {
                    b[position-4].changevalue(b[position-4].getvalue()*2);
                    b[position].changevalue(0);
                    b[position-4].iscomputed=true;
                    ischange=true;
                }
                if(b[position-4].getvalue()==0) {
                    b[position-4].changevalue(b[position].getvalue());
                    b[position].changevalue(0);
                    ischange=true;
                }
                position=position-4;
            }

        }
        if(ischange)
            randomone();
        is_fail_judge();
        if(notchange)
            System.out.println("you failed!");
    }


    private void is_fail_judge() {
        for(int i=0;i<16;i++) {
            if(b[i].getvalue()==0)
                return ;
            else {
                if( i%4-1>=0 && b[i].getvalue()==b[i-1].getvalue() )
                    return ;
                if( i%4+1<4 && b[i].getvalue()==b[i+1].getvalue() )
                    return ;
                if( i-4>=0 && b[i].getvalue()==b[i-4].getvalue() )
                    return ;
                if( i+4<16 && b[i].getvalue()==b[i+4].getvalue() )
                    return ;
            }
        }
        notchange=true;
        return ;

    }

    private void left_rotation(int[] intb) {
        b[0].changevalue(intb[3]);
        b[1].changevalue(intb[7]);
        b[2].changevalue(intb[11]);
        b[3].changevalue(intb[15]);
        b[4].changevalue(intb[2]);
        b[5].changevalue(intb[6]);
        b[6].changevalue(intb[10]);
        b[7].changevalue(intb[14]);
        b[8].changevalue(intb[1]);
        b[9].changevalue(intb[5]);
        b[10].changevalue(intb[9]);
        b[11].changevalue(intb[13]);
        b[12].changevalue(intb[0]);
        b[13].changevalue(intb[4]);
        b[14].changevalue(intb[8]);
        b[15].changevalue(intb[12]);
    }

    private void print_board() {
        for(int i=0;i<4;i++){
            System.out.println("");
            for(int j=0;j<4;j++)
                System.out.print(""+b[4*i+j].getvalue()+" ");
        }
        System.out.println("\n");
    }

    public int[] get_mux_b() {
        int [] intb= new int[16];

        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                intb[i*4+j]=b[i*4+j].getvalue();

        return intb;
    }
}


