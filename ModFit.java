// Scott Meyer
// 12/02/02
// Math Modeling
// ModFit.java
//
// Ongoing Project, Ver 0.5 
// An applet which performs transformations and model fitting using
// several variations of the least-squares criterion.  I thought an
// applet would be easiest for students to use on any computer while
// also providing a decent interface.  This is the main control class
// containing the GUI elements and final formulas.  Data is entered
// into a set of two ArrayList's, found in the Java.util package.
// This version has been streamlined considerably by the use of classes
// provide in the JVM rather than writing them myself.
//
//
// 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class ModFit implements ActionListener, ItemListener
   {
   
   private static final String buttonLabels[] = { "Add", "Undo", "Clear"/*, "Open", "Save" */};

   private static final String modeLabels[] = { "y = ax + b", "y = bx^a",
                                                "y = be^ax", "y = alnx + b",
                                                "y = ax^2", "y = ax^3",
	                                        "Quadratic", "Cubic", "Quartic", 
						"Splines" };

   private static final String valLabels[] = { "A:", "B:", "C:", "D:", "E:",
	                                       "Min Dev:", "Max Dev:" };
   static final int RESOLUTION = 100;
   static final int SCALE_VALUE = 10;

   private static ArrayList xList, yList;
   private static int mode, splineIndex;
   private static double graph[];
   private static double xScale, yScale;
   private Formula form;

   //JFileChooser chooser;
   static JTabbedPane cardPane;
   static JPanel valPanel, dataFieldPanel, dataButtonPanel; 
   static Box mainBox, radioBox, fieldBox, dataBox;
   static JButton listButton[], calc, sNext; 
   static JCheckBox sAll;
   static JLabel inputLabel[]; 
   static JTextField outputField[], inputField[];
   static JRadioButton modeButton[];
   static JScrollPane dataPane;
   static JTextArea dataArea;
   static ButtonGroup modes;
   static GraphPanel mainGraphPanel;

   public static void main(String[] args) {
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
    });
    
    }
   
   public static void createAndShowGUI()
      {
      xList = new ArrayList();
      yList = new ArrayList();
      //chooser = new JFileChooser( ".", new FileSystemView() );
      mode = 0;
      splineIndex = 0;
      xScale = 1.0;
      yScale = 1.0;
      graph = new double[ RESOLUTION ];

      Container c = getContentPane();
      c.setLayout( new GridLayout( 1, 2 ) ); 

      cardPane = new JTabbedPane();

	 dataBox = new Box( BoxLayout.Y_AXIS );
      
            dataFieldPanel = new JPanel( new FlowLayout() );
	    inputLabel = new JLabel[2];
	    inputField = new JTextField[2];
            inputLabel[0] = new JLabel( "X:" );
            inputLabel[1] = new JLabel( "Y:" );
	    for( int i = 0; i < inputLabel.length; i++ )
	       {
               inputField[i] = new JTextField( 10 );
               dataFieldPanel.add( inputLabel[i] );
               dataFieldPanel.add( inputField[i] );
	       }

            dataButtonPanel = new JPanel( new FlowLayout() );
	    listButton = new JButton[3];
	    for( int i = 0; i < listButton.length; i++ )
	       {
	       listButton[i] = new JButton( buttonLabels[i] );
               listButton[i].addActionListener( this );
               dataButtonPanel.add( listButton[i] );
	       }

            dataArea = new JTextArea( 15, 30 );
            dataArea.setEditable( false );
            dataPane = new JScrollPane( dataArea );
            dataPane.setColumnHeaderView( new JLabel( "Data", JLabel.CENTER ) );

         dataBox.add( dataFieldPanel );
         dataBox.add( dataButtonPanel );
         dataBox.add( dataPane );
         dataBox.setFocusTraversalPolicy( new LayoutFocusTraversalPolicy() );
         dataBox.setFocusCycleRoot( true );

         valPanel = new JPanel( new BorderLayout() );

            radioBox = new Box( BoxLayout.Y_AXIS );
            //radioBox.setAlignmentY( Box.CENTER_ALIGNMENT );
            modes = new ButtonGroup();
            modeButton = new JRadioButton[10];
            for( int i = 0; i < modeButton.length; i++ )
              {
	      modeButton[i] = new JRadioButton( modeLabels[i] );
              modeButton[i].addItemListener( this );
              modes.add( modeButton[i] );
              radioBox.add( modeButton[i] );
              }

            fieldBox = new Box( BoxLayout.Y_AXIS );
	    outputField = new JTextField[7];
	    for( int i = 0; i < outputField.length; i++ )
	       {
               outputField[i] = new JTextField( 20 );
               outputField[i].setEditable( false );
               fieldBox.add( new JLabel( valLabels[i] ) );
               fieldBox.add( outputField[i] );
	       }

            calc = new JButton( "Calculate" );
            calc.addActionListener( this );
	    radioBox.add( calc );
	    sNext = new JButton( "Next Spline" );
	    sNext.addActionListener( this );
	    sNext.setEnabled( false );
	    radioBox.add( sNext );
	    sAll = new JCheckBox( "Show All" );
	    sAll.setEnabled( false );
	    radioBox.add( sAll );

         valPanel.add( radioBox, BorderLayout.WEST );
         valPanel.add( fieldBox, BorderLayout.EAST );
         //valPanel.add( calc, BorderLayout.SOUTH );

      cardPane.add( "Data", dataBox );
      cardPane.add( "Models", valPanel );
      
      mainGraphPanel = new GraphPanel();
      //mainGraphPanel.setSize( 400, 400 );
      mainGraphPanel.setOpaque( true );
      mainGraphPanel.setBackground( Color.white );
      mainGraphPanel.setVisible( true );

      c.add( cardPane );
      c.add( mainGraphPanel );
      getRootPane().setBorder( BorderFactory.createLineBorder( Color.black ) );
      }

   public void actionPerformed( ActionEvent e )
      {
      if( e.getSource() == listButton[0] )
         {
         try
            {
            this.add( new Double( inputField[0].getText() ), 
	              new Double( inputField[1].getText() ) );
            dataArea.setText( this.printList() );
	    for( int i = 0; i < inputField.length; i++ )
               inputField[i].setText( new String() );
            //System.out.println( this.printList() );
            }
         catch( NumberFormatException nfe )
            {
            JOptionPane.showMessageDialog( this, "Numbers Only, Please :)",
                        "Invalid Number Format", JOptionPane.WARNING_MESSAGE );
            }
         }
      else if( e.getSource() == listButton[1] )
         {
         try
            {
            this.undo( xList.size() - 1 );
            }
         catch( IndexOutOfBoundsException ioobe )
            {
            JOptionPane.showMessageDialog( this, "Sorry, No Data Left.",
                        "Index Out Of Bounds", JOptionPane.WARNING_MESSAGE );
            } 
         dataArea.setText( this.printList() ); 
         }
      else if( e.getSource() == listButton[2] )
         {
         yList.clear();
         xList.clear();
         dataArea.setText( new String() );
	 mainGraphPanel.stopGraph();
         }
      /* else if( e.getSource() == listButton[3] )
         {
         int returnVal = chooser.showSaveDialog( null );
         if( returnVal == JFileChooser.APPROVE_OPTION );
            saveVals( chooser.getSelectedFile() );
         }
      else if( e.getSource() == listButton[4] )
         {
         int returnVal = chooser.showOpenDialog( null );
         if( returnVal == JFileChooser.APPROVE_OPTION );
            loadVals( chooser.getSelectedFile() );
         } */
      else if( e.getSource() == calc )
         {
         calculate();
	 mainGraphPanel.startGraph();
	 }
      else if( e.getSource() == sNext )
         {
	 double vals[] = new double[2];
         splineIndex++;
	 //System.out.println( form.getParams() );
         System.arraycopy( ( (Splines) form ).getParams( splineIndex ), 0, vals, 0, 2 );
         for( int i = 0; i < vals.length; i++ )
            outputField[i].setText( String.valueOf( vals[i] ) );
         if( sAll.isSelected() )
	    plotLine( form );
	 else
            plotLine( ( (Splines) form ).getSpline( splineIndex ) );
	 }
      repaint();
      }

   public void itemStateChanged( ItemEvent e )
      {
      if( e.getStateChange() == ItemEvent.SELECTED ) //ignore deselected
         for( int i = 0; i < modeButton.length; i++ )
            if( e.getItem() == modeButton[i] )
               {
               mode = i;
               //System.out.println( String.valueOf( i ) );
               break;
               }
      if( mode != 9 )
         {
	 sNext.setEnabled( false );
	 sAll.setEnabled( false );
	 sAll.setSelected( false);
	 }
      }

   private void add( Double x, Double y )
      {
      xList.add( x );
      yList.add( y );
      }

   private void undo( int index ) throws IndexOutOfBoundsException
      {
      try
         {
         xList.remove( index );
         yList.remove( index );
         }
      catch( IndexOutOfBoundsException ioobe )
         {
         throw ioobe;
         }
      }

   private Double getMax( ArrayList list )
      {
      try
         {
         TreeSet temp = new TreeSet( list );
         return (Double) temp.last();
	 }
      catch( NoSuchElementException nsee )
         {
	 return new Double( 0.0 );
	 }
      }

   private Double getMin( ArrayList list )
      {
      try
         {
         TreeSet temp = new TreeSet( list );
         return (Double) temp.first();
	 }
      catch( NoSuchElementException nsee )
         {
	 return new Double( 0.0 );
	 }
      }

   private double[] plotLine( Formula form )
      {
      double returnVal[] = new double[2];
      double dev, maxdev = 0, totaldev = 0; 
      double maxY = getMax( yList ).doubleValue();
      double maxX = getMax( xList ).doubleValue();

      for( int i = 0; i < xList.size(); i++ )
         {
	 dev = Math.abs( ( (Double) yList.get( i ) ).doubleValue() - 
	      form.getY( ( (Double) xList.get( i ) ).doubleValue() ) );
         if( dev > maxdev ) 
	    maxdev = dev;
	 totaldev += Math.pow( dev, 2.0 );
	 }
      
      returnVal[0] = Math.sqrt( totaldev / xList.size() );
      returnVal[1] = maxdev;

      for( int j = 0; j < RESOLUTION; j++ ) 
         { 
	 //System.out.println( j + ":\t" + form.getY( xScale/100.0 * (double) j) ); 
	 graph[j] = ( form.getY( xScale / RESOLUTION * j ) / yScale ); 
	 }
      return returnVal;
      }

   private String printList()
      {
      StringBuffer temp = new StringBuffer();
      for( int i = 0; i < xList.size(); i++ )
         temp.append( xList.get( i ) + "\t" + yList.get( i ) + "\n" );
      return temp.toString();
      }

   /* private void saveVals( File file )
      {
      try
         {
         FileWriter writer = new FileWriter( file );
         String output = printList();
         writer.write( output.toCharArray(), 0, output.length() );
         writer.close();
         }
      catch( IOException ioe )
         {
         JOptionPane.showMessageDialog( this, ioe.getMessage(), "IO Error", 
                                        JOptionPane.WARNING_MESSAGE );
         }
      }

   private void loadVals( File file )
      {
      try
         {
         Double x, y;
         FileReader reader = new FileReader( file );
         StreamTokenizer vals = new StreamTokenizer( reader );
         vals.parseNumbers();
         vals.eolIsSignificant( false );
         while( vals.nextToken() != StreamTokenizer.TT_EOF )
            {
            x = new Double( vals.nval );
            vals.nextToken();
            y = new Double( vals.nval );
            this.add( x, y );
            }
         reader.close();
         }
      catch( IOException ioe )
         { 
         JOptionPane.showMessageDialog( this, ioe.getMessage(), "IO Error",
                                        JOptionPane.WARNING_MESSAGE );
         }
      dataArea.setText( this.printList() );
      } */

   private void calculate()
      {
      double vals[] = new double[7];
      Double model[] = new Double[] {};
      switch( mode )
         {
         case 0:   form = new Linear( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
                   break;

         case 1:   form = new PowerOfX( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
                   break;

         case 2:   form = new PowerOfE( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
                   break;

         case 3:   form = new LnX( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
                   break;

         case 4:   form = new X2( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
                   break;

         case 5:   form = new X3( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
                   break;

         case 6:   form = new Quadratic( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
		   break;

         case 7:   form = new Cubic( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
                   break;

         case 8:   form = new Quartic( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
	           break;

	 case 9:   form = new Splines( (Double[]) xList.toArray( model ), (Double[]) yList.toArray( model ) );
	           sNext.setEnabled( true );
		   sAll.setEnabled( true );
		   sAll.setSelected( true );
         }
      System.arraycopy( form.getParams(), 0, vals, 0, form.getParams().length );
      System.arraycopy( plotLine( form ), 0, vals, vals.length - 2, 2 );
      for( int i = 0; i < vals.length; i++ )
         outputField[i].setText( String.valueOf( vals[i] ) );
      }

   private class GraphPanel extends JPanel
      {

      private boolean graphing;

      public GraphPanel()
         {
	 super();
	 graphing = false;
	 }

      protected void startGraph()
         {
	 graphing = true;
	 }

      protected void stopGraph()
         {
	 graphing = false;
	 }

      public void paint( Graphics g )
         {
	 double maxX = getMax( xList ).doubleValue();
	 double maxY = getMax( yList ).doubleValue();
	 xScale = 1.0;
	 yScale = 1.0;
	 //g.setColor( Color.blue );
	 g.clearRect( 0, 0, mainGraphPanel.getWidth(), mainGraphPanel.getHeight() );
	 g.translate( 0, mainGraphPanel.getHeight() );
	 g.setColor( Color.black );
	 g.setFont( g.getFont().deriveFont( (float) 10 ) );

         while( xScale < maxX )
            xScale *= SCALE_VALUE;

         while( yScale < maxY )
            yScale *= SCALE_VALUE;

	 for( int i = 0; i < mainGraphPanel.getWidth(); i += mainGraphPanel.getWidth() / 10 )
	    g.drawLine( i, 0, i, -10 );

	 for( int i = 0; i< mainGraphPanel.getHeight(); i += mainGraphPanel.getHeight() / 10 )
	    g.drawLine( 0, -i, 10, -i );

	 g.drawString( String.valueOf( xScale ), 
		      mainGraphPanel.getWidth() - String.valueOf( xScale ).length() * 10,
		      0 );

         g.drawString( String.valueOf( yScale ), 0, 
		      - mainGraphPanel.getHeight() + 10 );

	 for( int i = 0; i < xList.size(); i++ )
	    g.fillRect( (int) ( ( (Double) xList.get( i ) ).doubleValue() / 
	                   xScale * (double) mainGraphPanel.getWidth() ) - 2, 
	                (int) - ( ( (Double) yList.get( i ) ).doubleValue() / 
			   yScale * (double) mainGraphPanel.getHeight() ) - 2,
		         4, 4 );

         if( graphing )
	    {
	    g.setColor( Color.red );
	    for( int j = 0; j < RESOLUTION - 1; j++ )
	       g.drawLine( j * mainGraphPanel.getWidth() / RESOLUTION, 
	             (int) ( - graph[j] * (double) mainGraphPanel.getHeight() ), 
			   ( j + 1 ) * mainGraphPanel.getWidth() / RESOLUTION, 
		     (int) ( - graph[j + 1] * (double) mainGraphPanel.getHeight() ) );
	    }
	 }

      }

   }
      
