import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.swing.border.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;


import java.sql.*;


import javax.imageio.ImageIO;

//FOR REMOVE COMBOBOX SHADOW----------------------------------------------------------------
class NewTheme extends OceanTheme {
    public ColorUIResource getControlShadow() {
        return new ColorUIResource(255, 255, 255); // Set your custom shadow color here
    }
}
// END

public class App extends JPanel implements FocusListener, ActionListener {

    // Label,CHECKBOXS,TEXTARE
    // ETC...------------------------------------------------------

    JLabel camera, camerapoint, ntitle, profile, saveddata, info, setting, mtitle, luser, lname, lemail, lfathername,
            lmothername, laddress, lcity, lstate, lpincode, lcountry, luser1, lregino, lrollno, lclass, lsex, ldob,
            lcaste, lhobby, linstitution, lmobileno, lword;
    int grant;
    
    Border border, border1, border2, border3;
    JTextField tfname, tfemail, tffathername, tfmothername, tfcity, tfstate, tfpincode, tfregino, tfrollno, tfcaste,
            tfhobby, tfinstitution, tfmobileno,tfsearch,tfcountry,tfclass,tfsex,tfdate;
    JTextArea taaddress;
    JButton tab1, tab2, tab3, tab4, nextbutoon, prevbutoon, submitbutoon,search;
    private Connection connection;

    // font style

    Font f1 = new Font("Poppins", Font.BOLD, 18);
    Font f2 = new Font("Poppins", Font.PLAIN, 14);
    Font f3 = new Font("COMIC SANS MS", Font.BOLD, 20);

    // Color section--------------------------------------------------------------
    Color c1 = new Color(248, 249, 254); // snow backcolor box
    Color c2 = new Color(255, 255, 255); // white #fff
    Color c3 = Color.decode("#8E2DE2"); // puple color
    Color c4 = new Color(220, 220, 220); // light gray
    Color c5 = Color.decode("#070708");// Font color black
    Color c6 = new Color(124, 252, 0); // darkgreen
    Color c7 = new Color(197, 197, 197, 64); // shadow
    Color c8 = Color.decode("#FCFEF7"); // font white
    Color c9 = new Color(164, 118, 225, 36);
    // shadow box purple
    Color c10 = new Color(164, 118, 225, 70);
    // shadow box purple
    Color c11 = new Color(197, 197, 197); // shadow box
    Color c12 = new Color(66, 66, 66, 60); // new shadow box
    Color c13 = new Color(74, 0, 224, 100); // butoonand tfborder
    Color c14 = Color.decode("#858893");// Font color light

    Color c15 = new Color(164, 118, 225, 70);
    // shadow box purple
    Color c16 = new Color(164, 118, 225, 150);
    Color c17 = Color.decode("#4A00E0");// Font blue dark df
    Color c18 = Color.decode("#65FE08");// SUBMIT GREEN
    Color c19 = Color.decode("#F5F5F5");// box insider grays

    // LABEL,BORDER FOR IMAGE
    // UPLOADE-------------------------------------------------------------------
    private JButton uploadButton;
    private JButton resetButton;
    private JLabel imageLabel;
    private BufferedImage defaultImage;
    private BufferedImage uploadedImage;

    Color c21 = new Color(74, 0, 224, 100); // shadow box purple
    Color c20 = new Color(197, 197, 197); // shadow box purple

    public App() {

        initializeDatabase();

        // Coustom font---------------------------------------------------------------
        Font customFont = null;
        Font cf1 = null;
        Font cf3 = null;
        Font cf5 = null;

        try {
            // Set the look and feel to MetalLookAndFeel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            // Set your custom theme
            MetalLookAndFeel.setCurrentTheme(new NewTheme());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Load the font file
            File fontFile = new File("Poppins-Medium.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(20f);
            cf1 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(18f);
            cf3 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(13f);
            cf5 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(13f);

            // Register the font with the graphics environment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            ge.registerFont(cf1);
            ge.registerFont(cf3);
            ge.registerFont(cf5);

        } catch (Exception e) {
            // Print any exceptions that occur during font loading
            e.printStackTrace();
        }

        Font cf2 = null;
        Font cf4 = null;

        try {
            // Load the font file
            File fontFile = new File("Poppins-SemiBold.ttf");
            cf2 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(20f);
            cf4 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(21f);

            // Register the font with the graphics environment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            ge.registerFont(cf2);
            ge.registerFont(cf4);

        } catch (Exception e) {
            // Print any exceptions that occur during font loading
            e.printStackTrace();
        }

        // FOR UPLOAD
        // IMAGE--------------------------------------------------------------------------
        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(null); // Using null layout
        circlePanel.setBackground(c2);
        // Load the default image
        ImageIcon defaultImageIcon = new ImageIcon("camera.jpg"); // Path to your default image
        defaultImage = loadImage("camera.jpg");

        // Create a blank image with a circular border and set the default image as its
        // content
        int imageSize = 90; // Adjust the size of the default image and the circular border
        BufferedImage blankImage = createCircularImage(defaultImage, imageSize);

        // Initialize imageLabel with the default image
        ImageIcon circleImageIcon = new ImageIcon(blankImage);
        imageLabel = new JLabel(circleImageIcon);
        imageLabel.setBounds(25, 8, imageSize, imageSize); // Set bounds for the image label
        circlePanel.add(imageLabel);

        // Initialize upload button
        uploadButton = new JButton("Upload");
        uploadButton.addActionListener(this);
        uploadButton.setBounds(450, 45, 80, 30); // Set bounds for the button
        uploadButton.setBackground(c2);
        uploadButton.setForeground(c5);
        uploadButton.setBorder(BorderFactory.createLineBorder(c21));
        circlePanel.add(uploadButton);
        // UPLOAD IMAGE
        // END----------------------------------------------------------------







        JPanel mpanel = new JPanel();
        JPanel innerPanel1 = new JPanel();
        JPanel innerPanel2 = new JPanel();
        JPanel innerPanel3 = new JPanel();
        JPanel innerPanel4 = new JPanel();
        JPanel innerPanel5 = new JPanel();
        JPanel innerPaneltab2 = new JPanel();
        JPanel innerPaneltab3 = new JPanel();
        JPanel innerPaneltab21 = new JPanel();
        JPanel innerPaneltab31 = new JPanel();

        innerPanel1.setBackground(c2);
        innerPanel1.setBounds(0, 0, 1135, 60);
        innerPanel1.setLayout(null);

        innerPanel2.setBackground(c1);
        innerPanel2.setBounds(0, 0, 580, 600);
        innerPanel2.setLayout(null);

        innerPanel3.setBackground(c2);
        innerPanel3.setBounds(0, 20, 580, 110);
        innerPanel3.setLayout(new BorderLayout()); // Use BorderLayout for innerPanel3

        innerPanel4.setBackground(c2);
        innerPanel4.setBounds(0, 140, 580, 500);
        innerPanel4.setLayout(null);

        innerPanel5.setBackground(c1);
        innerPanel5.setBounds(250, 80, 250, 300);
        innerPanel5.setLayout(null);

        innerPaneltab2.setBackground(c1);
        innerPaneltab2.setBounds(0, 0, 250, 300);
        innerPaneltab2.setLayout(null);

        innerPaneltab21.setBackground(c2);
        innerPaneltab21.setBounds(0, 20, 580, 550);
        innerPaneltab21.setLayout(null);

        innerPaneltab3.setBackground(c1);
        innerPaneltab3.setBounds(0, 0, 250, 300);
        innerPaneltab3.setLayout(null);

        innerPaneltab31.setBackground(c2);
        innerPaneltab31.setBounds(0, 20, 580, 550);
        innerPaneltab31.setLayout(null);

        mpanel.setBackground(c1);
        mpanel.setBounds(0, 0, 1150, 750);
        mpanel.setLayout(null);

        GradientPanel p = new GradientPanel();
        p.setLayout(null);
        p.setBounds(0, 0, 220, 750);

        mpanel.add(p);
        mpanel.add(innerPanel1);
        mpanel.add(innerPanel5);
        innerPanel2.add(innerPanel3);
        innerPanel2.add(innerPanel4);
        innerPaneltab2.add(innerPaneltab21);
        innerPaneltab3.add(innerPaneltab31);
        mpanel.add(innerPanel2);


        //FOR REMOVE BORDER OF TABPAN ------------------------------
        JTabbedPane tp = new JTabbedPane();
        tp.setUI(new BasicTabbedPaneUI() {
            private final Insets borderInsets = new Insets(0, 0, 0, 0);

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }

            @Override
            protected Insets getContentBorderInsets(int tabPlacement) {
                return borderInsets;
            }
        });
//---------------------------

        tp.setBounds(530, 30, 580, 800);
        tp.add(innerPanel2);
        tp.add(innerPaneltab2); // Replace with your panel p2
        tp.add(innerPaneltab3); // Replace with your panel p3

        mpanel.add(tp);

        innerPanel3.add(circlePanel, BorderLayout.CENTER); // Add it to the left side

        add(mpanel);

        // image insert
        // start---------------------------------------------------------------------------------

        ImageIcon imageIcon = new ImageIcon("admin.png");
        Image image = imageIcon.getImage(); // Transform it into Image object
        Image newImage = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // Scale it to the new size
        ImageIcon scaledImageIcon = new ImageIcon(newImage); // Transform it back to ImageIcon
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setForeground(Color.white);
        imageLabel.setBounds(30, 102, newImage.getWidth(null), newImage.getHeight(null));
        p.add(imageLabel);

        ImageIcon imageIcon1 = new ImageIcon("info.png");
        Image image1 = imageIcon1.getImage();
        Image newImage1 = image1.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon1 = new ImageIcon(newImage1);
        JLabel imageLabel1 = new JLabel(scaledImageIcon1);
        imageLabel1.setForeground(Color.white);
        imageLabel1.setBounds(30, 152, newImage1.getWidth(null), newImage1.getHeight(null));
        p.add(imageLabel1);

        ImageIcon imageIcon2 = new ImageIcon("savedata.png");
        Image image2 = imageIcon2.getImage();
        Image newImage2 = image2.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon2 = new ImageIcon(newImage2);
        JLabel imageLabel2 = new JLabel(scaledImageIcon2);
        imageLabel2.setForeground(Color.white);
        imageLabel2.setBounds(30, 202, newImage2.getWidth(null), newImage1.getHeight(null));
        p.add(imageLabel2);

        ImageIcon imageIcon3 = new ImageIcon("setting.png");
        Image image3 = imageIcon3.getImage();
        Image newImage3 = image3.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon3 = new ImageIcon(newImage3);
        JLabel imageLabel3 = new JLabel(scaledImageIcon3);
        imageLabel3.setForeground(Color.white);
        imageLabel3.setBounds(30, 254, newImage3.getWidth(null), newImage1.getHeight(null));
        p.add(imageLabel3);

        // end--------------------------------------------------------------------------------------------

        // more--------------------------------------------------------------------
        // label,checkbox etc-----------------------------------------------

        camera = new JLabel("Upload a New Photo");
        camerapoint = new JLabel("Profile.pic.jpg");
        ntitle = new JLabel("<html><span style='font-weight: bold; '>weblance</span></html>");
        profile = new JLabel("Profile");
        saveddata = new JLabel("Saved Data");
        info = new JLabel("Information");
        setting = new JLabel("Setting");
        mtitle = new JLabel("UPDATE RECORD");

        tab1 = new JButton("<html>User Information<br><font size='-2'>About personal information</font></html>");
        tab2 = new JButton("<html>User Information 2<br><font size='-2'>About school/collage & submit</font></html>");
        tab3 = new JButton("<html>Massage<br><font size='-2'>Welcome message for user</font></html>");
        tab4 = new JButton("<html>Cancel<br><font size='-2'>Cancel butoon for clear form</font></html>");

        luser = new JLabel("Update User Information here");
        lname = new JLabel("Full Name*");
        lemail = new JLabel("Email*");
        lfathername = new JLabel("Father Name*");
        lmothername = new JLabel("Mother Name*");
        laddress = new JLabel("Address*");
        lcity = new JLabel("City*");
        lstate = new JLabel("State*");
        lpincode = new JLabel("Pincode");
        lcountry = new JLabel("Country");
        search = new JButton("Search");

        tfname = new JTextField();
        tfemail = new JTextField();
        tffathername = new JTextField();
        tfmothername = new JTextField();
        taaddress = new JTextArea();
        tfcity = new JTextField();
        tfstate = new JTextField();
        tfpincode = new JTextField();
        tfsearch = new JTextField();

     

tfcountry = new JTextField();
        nextbutoon = new JButton("Next");
        lregino = new JLabel("Registration No.");
        lrollno = new JLabel("Roll No.");
        lsex = new JLabel("Gender");

        luser1 = new JLabel("Update User Information here");
        tfregino = new JTextField();
        tfrollno = new JTextField();
        lclass = new JLabel("Class");
        tfclass = new JTextField();
        ldob = new JLabel("Date of Birth");
        tfsex = new JTextField();
        tfdate = new JTextField();
      
        lcaste = new JLabel("Caste");
        tfcaste = new JTextField();
        lhobby = new JLabel("Hobby");
        tfhobby = new JTextField();
        linstitution = new JLabel("Institution Name");
        tfinstitution = new JTextField();
        lmobileno = new JLabel("Mobile No.");
        tfmobileno = new JTextField();
        prevbutoon = new JButton("Previous");
        submitbutoon = new JButton("Submit");
        lword = new JLabel("<html><h1>Welcome Message</h1><p style='width: 400px;'>"
                + "Thank you for registering with our school. We are thrilled to welcome you "
                + "to our vibrant and diverse educational community! By registering, you have taken an important "
                + "step towards shaping your future and unlocking new opportunities for learning "
                + "and personal growth. As a valued member of our school, you will have access to an array "
                + "of enriching academic programs, exciting extracurricular activities, and dedicated support services "
                + "designed to help you thrive both academically and personally. Our passionate faculty and staff are committed "
                + "to providing you with a nurturing and stimulating learning environment where you can develop "
                + "critical thinking skills, foster creativity, and cultivate lifelong friendships. We eagerly anticipate "
                + "accompanying you on your educational journey and empowering you to achieve your aspirations. Should you "
                + "have any questions or require assistance, please do not hesitate to reach out to us. Once again, "
                + "a warm welcome to our school family - we're excited to embark on this educational adventure with you!"
                + "</p></html>");

        // label,checkbox etc end----------------------------------------------

        tab1.setBackground(c2);
        tab2.setBackground(c2);
        tab3.setBackground(c2);
        tab4.setBackground(c2);
        search.setBackground(c2);

        nextbutoon.setBackground(c17);
        prevbutoon.setBackground(c2);
        submitbutoon.setBackground(c17);
        tfcountry.setBackground(c2);
        tfclass.setBackground(c2);
        tfsex.setBackground(c2);
        tfdate.setBackground(c2);
       

        // JComboBox<String> cbcountry,cbclass,cbsex,cbdate,cbmonth,cbyear;

        // bounds
        // ===========================================================================

        camera.setBounds(130, 25, 200, 50);
        camerapoint.setBounds(132, 56, 150, 30);
        ntitle.setBounds(50, 0, 300, 50);
        profile.setBounds(70, 100, 200, 30);
        saveddata.setBounds(70, 150, 200, 30);
        info.setBounds(70, 200, 200, 30);
        setting.setBounds(70, 250, 200, 30);
        mtitle.setBounds(600, 15, 200, 30);
        tab1.setBounds(10, 30, 230, 60);
        tab2.setBounds(10, 100, 230, 60);
        tab3.setBounds(10, 170, 230, 60);
        tab4.setBounds(10, 240, 230, 60);

        luser.setBounds(50, 33, 400, 60);
        lname.setBounds(50, 76, 200, 50);
        tfname.setBounds(50, 111, 230, 25);
        lemail.setBounds(300, 76, 200, 50);
        tfemail.setBounds(300, 111, 230, 25);
        lfathername.setBounds(50, 141, 200, 50);
        tffathername.setBounds(50, 176, 230, 25);
        lmothername.setBounds(300, 141, 200, 50);
        tfmothername.setBounds(300, 176, 230, 25);
        laddress.setBounds(50, 201, 200, 50);
        taaddress.setBounds(50, 236, 480, 25);
        lcity.setBounds(50, 261, 200, 50);
        tfcity.setBounds(50, 296, 230, 25);
        lstate.setBounds(300, 261, 200, 50);
        tfstate.setBounds(300, 296, 230, 25);
        lpincode.setBounds(50, 331, 200, 50);
        tfpincode.setBounds(50, 366, 230, 25);
        lcountry.setBounds(300, 331, 200, 50);
        tfcountry.setBounds(300, 366, 230, 25);
        nextbutoon.setBounds(190, 430, 200, 30);
        lregino.setBounds(50, 65, 150, 50);
        tfregino.setBounds(50, 100, 230, 25);
        lrollno.setBounds(300, 65, 150, 50);
        tfrollno.setBounds(300, 100, 230, 25);
        lclass.setBounds(50, 130, 180, 50);
        tfclass.setBounds(50, 165, 230, 25);
        lsex.setBounds(300, 130, 200, 50);
        tfsex.setBounds(300, 165, 230, 25);
        ldob.setBounds(50, 195, 180, 50);
        tfdate.setBounds(50, 230, 105, 25);
     
        lcaste.setBounds(50, 260, 230, 50);
        tfcaste.setBounds(50, 295, 230, 25);
        lhobby.setBounds(300, 260, 200, 50);
        tfhobby.setBounds(300, 295, 230, 25);
        linstitution.setBounds(50, 328, 230, 50);
        tfinstitution.setBounds(50, 365, 230, 25);
        lmobileno.setBounds(300, 328, 230, 50);
        tfmobileno.setBounds(300, 365, 230, 25);
        luser1.setBounds(50, 15, 400, 60);
        prevbutoon.setBounds(135, 430, 150, 35);
        submitbutoon.setBounds(297, 430, 150, 35);
        lword.setBounds(50, 15, 500, 400);
        search.setBounds(300,15,90,25);
        tfsearch.setBounds(398,15,130,25);


        // bounds end
        // ===========================================================================

        border = new BevelBorder(BevelBorder.LOWERED, c10, c9);
        border1 = new BevelBorder(BevelBorder.LOWERED, c12, c11);
        border3 = new BevelBorder(BevelBorder.LOWERED, c16, c15);
        border2 = new BevelBorder(BevelBorder.LOWERED, c11, c2);

        tab1.setBorder(border);
        tab2.setBorder(border);
        tab3.setBorder(border);
        tab4.setBorder(border);
        innerPanel3.setBorder(border3);
        innerPanel4.setBorder(border3);
        innerPanel1.setBorder(border2);
        innerPaneltab21.setBorder(border3);
        innerPaneltab31.setBorder(border3);

        int borderWidth = 2; // Specify the desired border width
        tfname.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfemail.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfsearch.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tffathername.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfmothername.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        taaddress.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfcity.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfstate.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfpincode.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfcountry.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        nextbutoon.setBorder(BorderFactory.createLineBorder(c13));
        tfregino.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfrollno.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfclass.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfsex.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfdate.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
      
        tfcaste.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfhobby.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfinstitution.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        tfmobileno.setBorder(BorderFactory.createLineBorder(c13, borderWidth));
        prevbutoon.setBorder(BorderFactory.createLineBorder(c13));
        submitbutoon.setBorder(BorderFactory.createLineBorder(c13));
       


        // color
        // start-----------------------------------------------------------------------------
        search.setForeground(c14);
        tfsearch.setForeground(c5);
        ntitle.setForeground(c8);
        profile.setForeground(c8);
        saveddata.setForeground(c8);
        info.setForeground(c8);
        setting.setForeground(c8);
        mtitle.setForeground(c5);
        tab1.setForeground(c5);
        tab2.setForeground(c5);
        tab3.setForeground(c5);
        tab4.setForeground(c5);
        luser.setForeground(c5);
        lname.setForeground(c14);
        tfname.setForeground(c5);
        lemail.setForeground(c14);
        tfemail.setForeground(c5);
        lfathername.setForeground(c14);
        tffathername.setForeground(c5);
        lmothername.setForeground(c14);
        tfmothername.setForeground(c5);
        laddress.setForeground(c14);
        taaddress.setForeground(c5);
        lcity.setForeground(c14);
        tfcity.setForeground(c5);
        lstate.setForeground(c14);
        tfstate.setForeground(c5);
        lpincode.setForeground(c14);
        tfpincode.setForeground(c5);
        lcountry.setForeground(c14);
        tfcountry.setForeground(c5);
        nextbutoon.setForeground(c2);
        lregino.setForeground(c14);
        tfregino.setForeground(c5);
        lrollno.setForeground(c14);
        tfrollno.setForeground(c5);
        lclass.setForeground(c14);
        tfclass.setForeground(c5);
        lsex.setForeground(c14);
        tfsex.setForeground(c5);
        ldob.setForeground(c14);
        tfdate.setForeground(c5);
               lcaste.setForeground(c14);
        tfcaste.setForeground(c5);
        lhobby.setForeground(c14);
        tfhobby.setForeground(c5);
        linstitution.setForeground(c14);
        tfinstitution.setForeground(c5);
        lmobileno.setForeground(c14);
        tfmobileno.setForeground(c5);
        luser1.setForeground(c5);
        prevbutoon.setForeground(c5);
        submitbutoon.setForeground(c2);
        lword.setForeground(c5);

        // color end
        // -----------------------------------------------------------------------------

        // fontset
        // ========================================================================================
        search.setFont(cf3);
        tfsearch.setFont(cf3);
        camera.setFont(f1);
        camerapoint.setFont(f2);
        ntitle.setFont(customFont);
        profile.setFont(cf1);
        saveddata.setFont(cf1);
        info.setFont(cf1);
        setting.setFont(cf1);
        mtitle.setFont(cf2);
        tab1.setFont(cf1);
        tab2.setFont(cf1);
        tab3.setFont(cf1);
        tab4.setFont(cf1);
        luser.setFont(cf4);
        lname.setFont(cf3);
        tfname.setFont(cf3);
        lemail.setFont(cf3);
        tfemail.setFont(cf3);
        lfathername.setFont(cf3);
        tffathername.setFont(cf3);
        lmothername.setFont(cf3);
        tfmothername.setFont(cf3);
        laddress.setFont(cf3);
        taaddress.setFont(cf3);
        lcity.setFont(cf3);
        tfcity.setFont(cf3);
        lstate.setFont(cf3);
        tfstate.setFont(cf3);
        lpincode.setFont(cf3);
        tfpincode.setFont(cf3);
        lcountry.setFont(cf3);
        tfcountry.setFont(cf3);
        nextbutoon.setFont(cf3);
        lregino.setFont(cf3);
        tfregino.setFont(cf3);
        lrollno.setFont(cf3);
        tfrollno.setFont(cf3);
        lclass.setFont(cf3);
        tfclass.setFont(cf3);
        lsex.setFont(cf3);
        tfsex.setFont(cf3);
        ldob.setFont(cf3);
        tfdate.setFont(cf3);
              lcaste.setFont(cf3);
        tfcaste.setFont(cf3);
        lhobby.setFont(cf3);
        tfhobby.setFont(cf3);
        linstitution.setFont(cf3);
        tfinstitution.setFont(cf3);
        lmobileno.setFont(cf3);
        tfmobileno.setFont(cf3);
        luser1.setFont(cf4);
        prevbutoon.setFont(cf3);
        submitbutoon.setFont(cf3);
        lword.setFont(cf3);

        // fontset end
        // ========================================================================================

        // add
        // ========================================================================================

        circlePanel.add(camera);
        circlePanel.add(camerapoint);
        p.add(ntitle);
        p.add(profile);
        p.add(saveddata);
        p.add(info);
        p.add(setting);
        innerPanel1.add(mtitle);
        innerPanel5.add(tab1);
        innerPanel5.add(tab2);
        innerPanel5.add(tab3);
        innerPanel5.add(tab4);
        innerPanel4.add(luser);
        innerPanel4.add(search);
        innerPanel4.add(tfsearch);

        innerPanel4.add(lname);
        innerPanel4.add(tfname);
        innerPanel4.add(lemail);
        innerPanel4.add(tfemail);
        innerPanel4.add(lfathername);
        innerPanel4.add(tffathername);
        innerPanel4.add(lmothername);
        innerPanel4.add(tfmothername);
        innerPanel4.add(laddress);
        innerPanel4.add(taaddress);
        innerPanel4.add(lcity);
        innerPanel4.add(tfcity);
        innerPanel4.add(lstate);
        innerPanel4.add(tfstate);
        innerPanel4.add(lpincode);
        innerPanel4.add(tfpincode);
        innerPanel4.add(lcountry);
        innerPanel4.add(tfcountry);
        innerPanel4.add(nextbutoon);

        innerPaneltab21.add(luser1);
        innerPaneltab21.add(lregino);
        innerPaneltab21.add(tfregino);
        innerPaneltab21.add(lrollno);
        innerPaneltab21.add(tfrollno);
        innerPaneltab21.add(lclass);
        innerPaneltab21.add(tfclass);
        innerPaneltab21.add(lsex);
        innerPaneltab21.add(tfsex);
        innerPaneltab21.add(ldob);
        innerPaneltab21.add(tfdate);
               innerPaneltab21.add(lcaste);
        innerPaneltab21.add(tfcaste);
        innerPaneltab21.add(lhobby);
        innerPaneltab21.add(tfhobby);
        innerPaneltab21.add(linstitution);
        innerPaneltab21.add(tfinstitution);
        innerPaneltab21.add(lmobileno);
        innerPaneltab21.add(tfmobileno);
        innerPaneltab21.add(prevbutoon);
        innerPaneltab21.add(submitbutoon);

        innerPaneltab31.add(lword);

        // add end
        // ========================================================================================

        nextbutoon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tp.setSelectedIndex(1); // Select Tab 1
            }
        });
        prevbutoon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tp.setSelectedIndex(0); // Select Tab 1
            }
        });

        tab1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tp.setSelectedIndex(0); // Select Tab 1
            }
        });

        tab2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tp.setSelectedIndex(1); // Select Tab 2
            }
        });

        tab3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tp.setSelectedIndex(2); // Select Tab 3
            }
        });




        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndUpdate(); // Select Tab 3
            }
        });

        // FOCUSLINER

        tfname.addFocusListener(this);
        tfemail.addFocusListener(this);
        tffathername.addFocusListener(this);
        tfmothername.addFocusListener(this);
        taaddress.addFocusListener(this);
        tfcity.addFocusListener(this);
        tfstate.addFocusListener(this);
        tfpincode.addFocusListener(this);
        tfregino.addFocusListener(this);
        tfrollno.addFocusListener(this);
        tfcaste.addFocusListener(this);
        tfhobby.addFocusListener(this);
        tfinstitution.addFocusListener(this);
        tfmobileno.addFocusListener(this);

        tab4.addActionListener(this);
        submitbutoon.addActionListener(this);

        tab4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call resetImage() of circleImageDisplay when tab4 button is clicked
                resetImage();
                clrclass();
            }
        });


      
    }

    

    // Method to handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            // Handle upload button click
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    uploadedImage = ImageIO.read(selectedFile);
                    if (uploadedImage != null) {
                        System.out.println("Image uploaded successfully.");
                        // Resize and crop the image to fit the circular area
                        BufferedImage resizedImage = resizeAndCropToCircle(uploadedImage);
                        // Create a circular border for the uploaded image
                        ImageIcon uploadedImageIcon = createCircularBorder(new ImageIcon(resizedImage), c20);
                        // Update the imageLabel with the uploaded image
                        imageLabel.setIcon(uploadedImageIcon);
                    } else {
                        System.out.println("Failed to load uploaded image.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == submitbutoon) {
            // Handle submit button click
            // Convert uploaded image to byte array
            byte[] imageData = null;
            if (uploadedImage != null) {
                imageData = imageToByteArray(uploadedImage);
            }
            // Get other updated information from text fields
            String newName = tfname.getText();
            String newEmail = tfemail.getText();
            String newFatherName = tffathername.getText();
            String newMotherName = tfmothername.getText();
            String newAddress = taaddress.getText();
            String newCity = tfcity.getText();
            String newState = tfstate.getText();
            String newPincode = tfpincode.getText();
            String newCountry = tfcountry.getText();
            String newRegino = tfregino.getText();
            String newRollNo = tfrollno.getText();
            String newClass =  tfclass.getText();
            String newGender =  tfsex.getText();
            String newDOB =  tfdate.getText();
            String newCaste = tfcaste.getText();
            String newHobby = tfhobby.getText();
            String newInstitution = tfinstitution.getText();
            String newMobNo = tfmobileno.getText();
            
            // Call the updateDataInDatabase method with the new values
            updateDataInDatabase(newName, newEmail, newFatherName, newMotherName, newAddress, newCity, newState, newPincode, newCountry, newRegino, newRollNo, newClass, newGender, newDOB, newCaste, newHobby, newInstitution, newMobNo, imageData);
        } else if (e.getSource() == resetButton) {
            // Handle reset button click
            resetImage();
            clrclass();
        }
    }

    

    private byte[] imageToByteArray(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos); // You can change the format if needed
            return baos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to resize and crop the image to fit the circular area
    private BufferedImage resizeAndCropToCircle(BufferedImage image) {
        int imageSize = 90; // Adjust the size of the circular area
        BufferedImage resizedImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, imageSize, imageSize);
        g2d.setClip(circle);
        g2d.drawImage(image, 0, 0, imageSize, imageSize, null); // Draw the resized image within the circular area
        g2d.dispose();
        return resizedImage;
    }

    // Method to create a circular border for an image
    private ImageIcon createCircularBorder(ImageIcon imageIcon, Color borderColor) {
        int imageSize = imageIcon.getIconWidth(); // Get the size of the image
        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, imageSize, imageSize);
        g2d.setClip(circle);
        imageIcon.paintIcon(null, g2d, 0, 0);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(circle);
        g2d.dispose();
        return new ImageIcon(image);
    }

    // Method to reset the image to the default one
    public void resetImage() {
        // Resize and crop the default image to fit the circular area
        BufferedImage resizedDefaultImage = resizeAndCropToCircle(defaultImage);
        // Create a circular border for the default image
        ImageIcon defaultImageIconWithBorder = createCircularBorder(new ImageIcon(resizedDefaultImage), c20);
        // Update the imageLabel with the default image
        imageLabel.setIcon(defaultImageIconWithBorder);
    }

    // Method to get the uploaded image data as a byte array
    public byte[] getImageData() {
        System.out.println("getImageData() called."); // Debug statement
        if (uploadedImage != null) {
            try {
                // Convert the uploadedImage to a byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(uploadedImage, "png", baos);
                byte[] imageData = baos.toByteArray();
                System.out.println("Image Data Length: " + imageData.length); // Debug statement
                return imageData;
            } catch (IOException e) {
                e.printStackTrace();
                return null; // Return null if there's an exception
            }
        }
        System.out.println("No uploaded image found."); // Debug statement
        return null;
    }

    // Method to create a circular image with a border
    private BufferedImage createCircularImage(BufferedImage image, int imageSize) {
        BufferedImage circularImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = circularImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, imageSize, imageSize);
        g2d.setClip(circle);
        g2d.drawImage(image, 0, 0, imageSize, imageSize, null);
        g2d.setColor(c20);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(circle);
        g2d.dispose();
        return circularImage;
    }

    public void focusGained(FocusEvent fe) {
        fe.getComponent().setBackground(c19);
    }

    public void focusLost(FocusEvent fe) {
        fe.getComponent().setBackground(c2);
    }

    String def = "";

    public void clrclass() {
        tfname.setText(def);
        tfemail.setText(def);
        tffathername.setText(def);
        tfmothername.setText(def);
        taaddress.setText(def);
        tfcity.setText(def);
        tfstate.setText(def);
        tfpincode.setText(def);
        tfregino.setText(def);
        tfrollno.setText(def);
        tfcaste.setText(def);
        tfhobby.setText(def);
        tfinstitution.setText(def);
        tfmobileno.setText(def);
        tfclass.setText(def);
        tfcountry.setText(def);
        tfdate.setText(def);
               tfsex.setText(def);
             resetImage();

    }

    private Image byteArrayToImage(byte[] imageData) {
        try {
            // Read byte array into ByteArrayInputStream
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            // Convert ByteArrayInputStream to BufferedImage
            BufferedImage bufferedImage = ImageIO.read(bis);
            // Close ByteArrayInputStream
            bis.close();
            // Return BufferedImage
            return bufferedImage;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


   

    private void initializeDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weblance", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void searchAndUpdate() {
        String regino = tfsearch.getText();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM forminsert WHERE regino = ?")) {
            statement.setString(1, regino);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    tfname.setText(resultSet.getString("name"));
                    tfemail.setText(resultSet.getString("email"));
                    tffathername.setText(resultSet.getString("fathername"));
                    tfmothername.setText(resultSet.getString("mothername"));
                    taaddress.setText(resultSet.getString("address"));
                    tfcity.setText(resultSet.getString("city"));
                    tfstate.setText(resultSet.getString("state"));
                    tfpincode.setText(resultSet.getString("pincode"));
                    tfcountry.setText(resultSet.getString("country"));
                    tfregino.setText(resultSet.getString("regino"));
                    tfrollno.setText(resultSet.getString("rollno"));
                    tfclass.setText(resultSet.getString("classs"));
                    tfsex.setText(resultSet.getString("gender"));
                    tfdate.setText(resultSet.getString("dob"));
                    tfcaste.setText(resultSet.getString("caste"));
                    tfhobby.setText(resultSet.getString("hobby"));
                    tfinstitution.setText(resultSet.getString("institution"));
                    tfmobileno.setText(resultSet.getString("mobno"));
                    
                    // Retrieve image data
                    byte[] imageData = resultSet.getBytes("imageData");
                    if (imageData != null && imageData.length > 0) {
                        // Convert byte array to BufferedImage
                        Image image = byteArrayToImage(imageData);
                        if (image != null) {
                            // Convert Image to BufferedImage
                            BufferedImage bufferedImage = toBufferedImage(image);
                            // Resize and crop the image to fit the circular area
                            BufferedImage resizedImage = resizeAndCropToCircle(bufferedImage);
                            // Create a circular border for the image
                            ImageIcon circularImageIcon = createCircularBorder(new ImageIcon(resizedImage), c20);
                            // Update the imageLabel with the circular image
                            imageLabel.setIcon(circularImageIcon);
                        } else {
                            System.err.println("Failed to convert byte array to Image.");
                        }
                    } else {
                        System.err.println("No image data found in database.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No record found for regino: " + regino,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while searching: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
    
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    
        // Draw the image on to the buffered image
        Graphics2D g2d = bimage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
    
        // Return the buffered image
        return bimage;
    }    
    
    // Method to update data in the database
    private void updateDataInDatabase(String newName, String newEmail, String newFatherName, String newMotherName, String newAddress, String newCity, String newState, String newPincode, String newCountry, String regino, String newRollNo, String newClass, String newGender, String newDOB, String newCaste, String newHobby, String newInstitution, String newMobNo, byte[] newImageData) {
        try {
            String updateQuery = "UPDATE forminsert SET name = ?, email = ?, fathername = ?, mothername = ?, address = ?, city = ?, state = ?, pincode = ?, country = ?, rollno = ?, classs = ?, gender = ?, dob = ?, caste = ?, hobby = ?, institution = ?, mobno = ?";
            
            // Check if image data is provided
            if (newImageData != null) {
                updateQuery += ", imageData = ?";
            }
            
            updateQuery += " WHERE regino = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                // Set parameters for the prepared statement
                statement.setString(1, newName != null ? newName : tfname.getText());
                statement.setString(2, newEmail != null ? newEmail : tfemail.getText());
                statement.setString(3, newFatherName != null ? newFatherName : tffathername.getText());
                statement.setString(4, newMotherName != null ? newMotherName : tfmothername.getText());
                statement.setString(5, newAddress != null ? newAddress : taaddress.getText());
                statement.setString(6, newCity != null ? newCity : tfcity.getText());
                statement.setString(7, newState != null ? newState : tfstate.getText());
                statement.setString(8, newPincode != null ? newPincode : tfpincode.getText());
                statement.setString(9, newCountry != null ? newCountry : tfcountry.getText());
                statement.setString(10, newRollNo != null ? newRollNo : tfrollno.getText());
                statement.setString(11, newClass != null ? newClass : tfclass.getText());
                statement.setString(12, newGender != null ? newGender : tfsex.getText());
                statement.setString(13, newDOB != null ? newDOB : tfdate.getText());
                statement.setString(14, newCaste != null ? newCaste : tfcaste.getText());
                statement.setString(15, newHobby != null ? newHobby : tfhobby.getText());
                statement.setString(16, newInstitution != null ? newInstitution : tfinstitution.getText());
                statement.setString(17, newMobNo != null ? newMobNo : tfmobileno.getText());
                
                // Set image data parameter if provided
                int parameterIndex = 18;
                if (newImageData != null) {
                    statement.setBytes(parameterIndex++, newImageData);
                }
                
                // Set regino parameter
                statement.setString(parameterIndex, regino);
                
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Data updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update data.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle SQLException appropriately (e.g., log error, display error message)
        }
    }
    // run
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            JFrame f = new JFrame();
            f.setContentPane(app);
            f.setSize(1150, 750);
            f.setResizable(false);
            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            f.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int choice = JOptionPane.showConfirmDialog(f,
                            "Are you sure you want to exit?",
                            "Exit Confirmation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (choice == JOptionPane.YES_OPTION) {

                        System.exit(0);
                    }
                }
            });
            f.setLayout(null);
            f.setVisible(true);
        });

    }
}

// For GradientPanel
// -----------------------------------------------------------------------
class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        Point startPoint = new Point(0, 0);
        Point endPoint = new Point(0, getHeight());

        LinearGradientPaint gradientPaint = new LinearGradientPaint(startPoint, endPoint,
                new float[] { 0.0f, 1.0f },
                new Color[] { Color.decode("#8E2DE2"), Color.decode("#4A00E0") });

        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}
