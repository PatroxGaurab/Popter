import java.awt.*;
import java.applet.*;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.*;

public class PopterApp extends Applet implements Runnable{
	Random randRow = new Random();
	private int distance;
	Color rectColor;
	String comment = "Wanna Fun..?? Play it..";
	int row,col=0;
	int cnt,randCnt1,randCnt2;
	Thread t = null; 
	int state; 
	boolean stopFlag = true;
	boolean forward;
	boolean isPressed,isClicked;
	int rectX, rectY,objY;
	int cirX;
	int xCnt,xCntTemp,yCnt;
	int[] cirY = new int[25];
	int[] x = new int[200];
	int[] y = new int[200];
	public void init()
	{
		resize(450,350);
		setBackground(Color.GREEN);
		t = new Thread(this);
		objY = 137;
		x[0] = 0;
		y[0] = 25;
		y[100] = 300;
		for(int i = 1;i<100; i++)
		{
			x[i] = x[i-1]+4;
			y[i] = 25;
		}
		x[100] = x[99];
		rectX = x[100];
		rectY = 25 + randRow.nextInt(225);
		for(int i = 101;i<200; i++)
		{
			x[i] = x[i-1]-4;
			y[i] = 300;
		}
		randCnt1 = 0;
		randCnt2 = 0;
		cirX = 75;
		distance = 0;
		
		addMouseListener(
				new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						stopFlag = false; 
						t.start();
						comment = "Try it Baby..!!";
						isClicked = true;
					}
					public void mousePressed(MouseEvent e)
					{
						isPressed = true;
						cnt = 20;
						xCntTemp = xCnt;
						xCnt = -4;
						yCnt = 1;
						//showStatus("Mouse Pressed : "+objY);
					}
					public void mouseReleased(MouseEvent e)
					{
						isPressed = false;
						isClicked = false;
						cnt = 20;
						xCntTemp = xCnt;
						xCnt = -4;
						yCnt = 1;
						//showStatus("Mouse Released : "+objY);
					}
					public void mouseExited(MouseEvent e)
					{
						showStatus("Pointer Is Outside Applet");
					}
					public void mouseEntered(MouseEvent e)
					{
						showStatus("Click The Window To Start Applet");
					}
				}
			);
	}
	public void start()
	{
	    forward = true;
	}
	public void run()
	{
		while(t != null){
			try{
				repaint();
				Thread.sleep(10);
				for(int i = 0;i<99; i++)
				{
					y[i] = y[i+1];
				}
				for(int i = 199;i>100; i--)
				{
					y[i] = y[i-1];
				}
				if(randCnt1 <= 0 && randCnt2 <= 0)
				{
					randCnt1 = randRow.nextInt(100);
					randCnt2 = randRow.nextInt(85);
					y[99] = randRow.nextInt(25);
					y[100] = y[99] + 300;
				}
				else
				{
					if( randCnt1 >=0 )
					{
						randCnt1--;
						y[99] = y[99] + 1;
						y[100] = y[99] + 300;
					}
					else
					{
						randCnt2--;
						y[99] = y[99] - 1;
						y[100] = y[99] + 300;
					}
				}
				rectX -= 4;
				
				if( rectX <= 0 )
				{
					rectX = x[100];
					rectY = 25 + randRow.nextInt(225);
				}
				
				/*if( cnt-- > 10 )
				{
					if(isPressed)
						objY += 0.5*xCntTemp;
					else
						objY -= 0.5*xCntTemp;
					for( int i = 0; i < 24; i++)
					{
						cirY[i] = cirY[i+1];
					}
					cirY[24] = objY;
					continue;
				}
				else
				{*/
					if(isPressed)
					{
						objY -= ((0.15*xCnt)); 
						yCnt++;
						//objY += 0.2*(xCnt*xCnt*xCnt);
					}
					else
					{
						objY += (0.2*xCnt);
						//yCnt--;
						//objY -= 0.2*(xCnt*xCnt*xCnt);
					}
				//}
				xCnt += 1;
				if(rectX <= 130 && rectX >= 65 && rectY >= (objY-80) && rectY <= (objY+25))
				{
					showStatus("Sorry.!Baby, this game is not for Kids like you...!!!");
					comment = "Sorry.!Baby, this game is not for Kids like you...!!!";
					setBackground(Color.WHITE);
					
					stop();
					//break;
					//init();
				}
				if(y[25] >= objY || y[32] >= objY || y[174] <= objY+25 || y[167] <= objY+25)
				{
					showStatus("Sorry.!Baby, this game is not for Kids like you...!!!");
					comment = "Sorry.!Baby, this game is not for Kids like you...!!!";
					setBackground(Color.WHITE);
					
					stop();
					break;
					//init();
				}
				
				for( int i = 0; i < 24; i++)
				{
					cirY[i] = cirY[i+1];
				}
				cirY[24] = objY;
				distance++;
				if( distance > 250 && distance <= 500)
				{
					comment = "Try it Hard";
				}
				if( distance > 500 && distance <= 1000)
				{
					comment = "You are there..!";
				}
				if( distance > 1000 && distance <= 2000)
				{
					comment = "Remenber, You have a Goal.!";
				}
				if( distance > 2000 )
				{
					comment = "What Do You Want..??";
				}
				showStatus("Distance : " + distance);
				//rectColor = new Color(randRow.nextInt(),randRow.nextInt(),randRow.nextInt());
			}catch(InterruptedException e) {}
		}
	}
	public void stop() {    
	    stopFlag = true; 
	    t = null; 
	  } 
	public void paint(Graphics g)
	{
		g.fillPolygon(x,y,200);
		
		g.setColor(Color.GREEN);
		g.fillRect(rectX, rectY, 35, 80);
		g.setColor(Color.CYAN);
		g.fillRect(100, objY, 30, 25);
		cirX = 100;
		g.setColor(Color.BLUE);
		if(stopFlag)
		{
			g.setFont(new Font( "Serif", Font.BOLD, 30));
			g.drawString("CLICK HERE",50,100);
			g.drawString("TO START",140,140);
			g.setFont(new Font( "Serif", Font.BOLD, 18));
			g.setColor(Color.RED);
			g.drawString("Developed by PATRA PRODUCTION__",50,50);
			g.drawString("The Best Software Manufacturer In The World",15,70);
		}
		g.setFont( new Font( "Serif", Font.BOLD, 24));
		g.drawString("Distance : " + distance, 3, 300);
		g.drawString(comment , 180, 300);
		int i = 24;
		while(cirX >= 0)
		{
			g.setColor(Color.WHITE);
			g.fillOval(cirX, cirY[i--], 5, 8);
			cirX -= 4;
		}
	}
	

}
