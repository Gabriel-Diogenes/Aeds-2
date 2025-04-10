import java.io.IOException;
import java.io.RandomAccessFile;

public class TP1Q14{
  
    public static void main(String[] args) throws IOException{
        
        RandomAccessFile raf = new RandomAccessFile("arquivo.txt", "rw");
        int x = MyIO.readInt();

        for(int y = 0; y < x; y++){
            double z = MyIO.readDouble();
            raf.writeDouble(z);
        }

        raf.close();

        raf = new RandomAccessFile("arquivo.txt", "r");

        for(int y = 0; y < x; y++){
            raf.seek((x - y - 1) * 8);
            double z = raf.readDouble();
            int z2 = (int)z;
            

            if(z == z2)
                MyIO.println(z2);
            else
                MyIO.println(z);
        }

        raf.close();
    }
}