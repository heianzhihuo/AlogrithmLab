package Search;

import java.awt.event.*;

import javax.swing.*;

public class Search implements ActionListener,Runnable {
	
	private Field field;
    View view;
    private JFrame frame;
    private JPanel panel;
    private JButton button[] = new JButton[6];
    private String[] buttonText = {"Source","Destination","Wall","Flat","River","Desert"};
    private JButton startButton,resetButton;
    private boolean isRun = true;
    Thread thread = new Thread(this);
    private int speed = 0;
    
    public Search(int width,int height) {
    	field = new Field(width, height);
    	
    	initData();
    	
    	view = new View(field);
        frame = new JFrame();
        panel = new JPanel();

        packButton();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("PathFinding");
        frame.add(panel, "North");
        frame.add(view);
        frame.pack();
        
        frame.setVisible(true);
        
        reset();
        thread.start();
    }
    
    private void packButton() {
    	startButton = new JButton();
    	startButton.setText("开始/暂停");
    	startButton.addActionListener(this);
    	panel.add(startButton);
    	
    	resetButton = new JButton();
    	resetButton.setText("重置");
    	resetButton.addActionListener(this);
    	panel.add(resetButton);
    	
    	for(int i=0;i<6;i++) {
    		button[i] = new JButton();
    		button[i].setText(buttonText[i]);
    		button[i].addActionListener(this);
    		panel.add(button[i]);
    	}
	}
    
    
    public void reset() {
    	initData();
    	speed = 400;
    	isRun = false;
    	frame.repaint();
    }
    
    @Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isRun) {
//				System.out.println("start");
				Point p = field.SelectNext();
				frame.repaint();
				if(p==null) {
					isRun = false;
					continue;
				}
//				try {
//					thread.sleep(speed);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				field.ExtendPoint(p);
				frame.repaint();
//				try {
//					thread.sleep(speed);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==startButton) {
			view.clearCommond();
			if(field.Start()==-1) {
				System.out.println("请确定起点和终点");
				isRun = false;
			}
			frame.repaint();
			isRun = !isRun;
		}else if(e.getSource()==resetButton) {
			reset();
			view.clearCommond();
		}else if(!isRun) {
			for(int i=0;i<6;i++)
				if(e.getSource()==button[i]) {
					view.setCommond(i+1);
					break;
				}
		}
	}

	private void initData() {
		for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                field.place(row, col, new Point(row,col));
            }
        }
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Search(64, 64);
	}
}
