package Demo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgPanel extends javax.swing.JPanel
{

    private static final long serialVersionUID = 1L;
    private Image image;
    private int imgWidth;
    private int imgHeight;



    public Image getImg()
    {
        return image;
    }


    public int getImgWidth()
    {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth)
    {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight()
    {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight)
    {
        this.imgHeight = imgHeight;
    }

    public ImgPanel()
    {
    }

    public void setImagePath(String imgPath) {
        //图像并不加载到内存，当拿图像的宽和高时会返回-1；
        // image = Toolkit.getDefaultToolkit().getImage(imgPath);
        try
        {
            // 该方法会将图像加载到内存，从而拿到图像的详细信息。
            image = ImageIO.read(new FileInputStream(imgPath));
            //读取图片的大小
            setImgWidth(image.getWidth(this));
            setImgHeight(image.getHeight(this));
            this.repaint();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g1)
    {
        super.paintComponent(g1);
        int x = 0;
        int y = 0;
        Graphics g = (Graphics) g1;
        if (null == image)
        {
            return;
        }

        g.drawImage(image, x, y, image.getWidth(this), image.getHeight(this),
                this);
        g = null;


    }
}
