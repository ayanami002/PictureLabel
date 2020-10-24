package Demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class PhotoFrame extends JFrame {

    private static final long serialVersionUID = -2216276219179107707L;
    private Container con;

    private MousePanel zPanel;
    private JScrollPane imgSp;
    private JPanel btnPanel;
    private String imageDir;
    private String currImg;//文件名，不包含目录
    private int currIndex;//当前图片索引
    ArrayList<String> imgList;//所有图片的绝对路径

    public void writeCropImg() throws IOException {

        BufferedImage sourceImage = (BufferedImage) zPanel.getImg();

        Image croppedImage;
        ImageFilter cropFilter;

        int x = zPanel.getPointX() - 25;
        int y = zPanel.getPointY() - 25;
        //四个参数分别为图像起点坐标和宽高，即CropImageFilter(int x,int y,int width,int height)，详细情况请参考API
        //指定要裁剪的的文件的宽度和高度，以及起始坐标
        cropFilter = new CropImageFilter(x, y, 50, 50);
        //生成图片
        croppedImage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(sourceImage.getSource(), cropFilter));

        //获取创建后的图片的高度
        int h1 = croppedImage.getHeight(null);
        int w1 = croppedImage.getWidth(null);

        BufferedImage bi = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);

        Graphics g = bi.getGraphics();
        //在画图的时候可以设置背景色
        g.drawImage(croppedImage, 0, 0, Color.white, null);

        String dir = imageDir + "_crops";

        //创建文件输出流
        FileOutputStream fos = new FileOutputStream(new File(dir + "/" + currImg));
        //将创建的图片写入到输出流
        ImageIO.write(bi, "png", fos);
        fos.close();
    }

    private void showNext() {
        System.out.println(currIndex);
       // updateCurrName();
        //System.out.println(imgList);
        zPanel.setImagePath(imgList.get(currIndex++));
        updateCurrName();

    }

    private void updateCurrName() {
        String path = imgList.get(currIndex);
        int index = path.lastIndexOf('\\');
        currImg = path.substring(index+1);

    }

    private class nextHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showNext();
        }
    }

    private class openDirHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//设置只能选择目录
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                imageDir = chooser.getSelectedFile().getPath();
                imgList = FileTool.refreshFileList(imageDir);
                currIndex = 0;
                showNext();
                //System.out.println ( "你选择的目录是：" + selectPath );
                //你选择的目录是：/home/linger/imdata/collar
            }

        }

    }

    private void savePointAndImg() {
        try {
            String dir = imageDir + "_points";
            int x1 = zPanel.getPointX();
            int y1 = zPanel.getPointY();
            int x2 = zPanel.getX_2();
            int y2 = zPanel.getY_2();
            System.out.println("(" + x1 + "," + y1 + "),(" + x2 + "," + y2 + ")");

            //打开文件
            File f = new File("D:\\cat_dog_data\\data\\" + currImg + ".txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f);
            fw.write("(" + x1 + "," + y1 + "),(" + x2 + "," + y2 + ")");
            fw.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private class savePointHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            savePointAndImg();
        }
    }

    private class openImageHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                imageDir = file.getParent();
                imgList = FileTool.refreshFileList(imageDir);
                currImg = file.getName();//文件名,不包含路径
                // System.out.println();
                String path = file.getAbsolutePath();
                currIndex = imgList.indexOf(path);
                zPanel.setImagePath(path);
                currIndex++;
                //内部类访问外部类,可以直接访问的啊

            }
        }
    }

    private PhotoFrame() {
        con = getContentPane();
        con.setLayout(new GridLayout(2, 2));

        zPanel = new MousePanel();
        //zPanel.setImagePath("/home/linger/17820d01");//绑定图片
        //zPanel.setPreferredSize(new Dimension(zPanel.getImgWidth(), zPanel.getImgHeight()));

        imgSp = new JScrollPane();
        imgSp.setPreferredSize(new Dimension(zPanel.getImgWidth(), zPanel.getImgHeight()));
        imgSp.setViewportView(zPanel);
        imgSp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        imgSp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //con.add(imgSp, BorderLayout.CENTER);
        con.add(imgSp);


        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setSize(300, 100);

        JButton button = new JButton("打开图片");
        button.setSize(100, 50);
        btnPanel.add(button);
        button.addActionListener(new openImageHandler());

        button = new JButton("保存坐标");
        button.setSize(100, 50);
        btnPanel.add(button);
        button.addActionListener(new savePointHandler());

        button = new JButton("打开目录");
        button.setSize(100, 50);
        btnPanel.add(button);
        button.addActionListener(new openDirHandler());

        button = new JButton("下一张");
        button.setSize(100, 50);
        btnPanel.add(button);
        button.addActionListener(new nextHandler());

        con.add(btnPanel);


        zPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    savePointAndImg();
                    showNext();
                }
            }
        });


        finalSetting();
    }

    private void finalSetting() {
        setTitle("标注工具");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int frameH = getHeight();
        int frameW = getWidth();
        setLocation((screenWidth - frameW) / 2 - 250,
                (screenHeight - frameH) / 2 - 250);

        setSize(800, 600);
        //setSize(zPanel.getImgWidth()+10, zPanel.getImgHeight()+10);
        //setPreferredSize(new Dimension(zPanel.getImgWidth()+100, zPanel.getImgHeight()+100));
        //pack();

        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new PhotoFrame();
    }

}
