package GUI;
import java.awt.EventQueue;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import search.Astar;
import search.Node;
import java.awt.Color;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;

import java.io.*;
import java.util.Stack;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.filechooser.*;

public class GUI extends JFrame{
	private Board board = null;
	private JPanel contentPane;
	private JButton btnload;
	private JButton btnsave;
	private JButton btncreate;
	private JTable table;
	private JComboBox comboBox;
	private JButton btnStart;
	private Board Res;
	private String[] header;
	private Stack<Node> Close;
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 * @param size 
	 */
	public JTable Draw (Object[][] str, String[] header) {
		table = new JTable(str,header) {
		    @Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {  
		        int modelRow = convertRowIndexToModel(row);  
		        int modelColumn = convertColumnIndexToModel(column);  
		        Component comp = super.prepareRenderer(renderer, row, column);  
		        if (!isRowSelected(modelRow)) {
		        		if(table.getValueAt(modelRow, modelColumn).toString().endsWith(".")) {
		                	comp.setBackground(Color.WHITE);
		                }
		                if (table.getValueAt(modelRow, modelColumn).toString().startsWith("b")) {
		                    comp.setBackground(Color.BLACK);
		                    comp.setForeground(Color.BLACK);
		                }else if(table.getValueAt(modelRow, modelColumn).toString().startsWith("r")||table.getValueAt(modelRow, modelColumn).toString().startsWith("SG")){      
		                	  comp.setBackground(Color.RED);
		                }else if(!table.getValueAt(modelRow, modelColumn).toString().endsWith(".")){
		                	comp.setBackground(Color.LIGHT_GRAY);
		                }
		               
		        }
		        return comp;
		    }
		};
		for(int i=0;i<header.length;i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(20);
		}
		table.setRowHeight(20);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);
		table.setTableHeader(null);
		return table;
	}
	public ActionListener re_save(Board board,final JFileChooser fileChooser) {
		final String[][] str = Board.toString(board);
		final int size = board.s+1;
		final int start = board.start;
		final int goal = board.goal;
		ActionListener ls = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            int i = fileChooser.showSaveDialog(null);
			            if(i==JFileChooser.APPROVE_OPTION){
			            	File file = fileChooser.getSelectedFile();
			            	String fname = fileChooser.getName(file);
			            	if(!(fname.endsWith(".map"))){
			    				file=new File(fileChooser.getCurrentDirectory(),fname+".map");
			            	}
			    			file.createNewFile(); 
			    			PrintWriter writer = new PrintWriter(new FileOutputStream(file),true);
			    			writer.println(String.valueOf(size));
			    			writer.println(String.valueOf(start));
			    			writer.println(String.valueOf(goal));
			                for (int r=0;r<size;r++) {
								for (int c= 0;c<size;c++) {
									if(str[r][c].equals("b")) {
										writer.println(String.valueOf(r*size+c));
									}
								}
							}
			                writer.close();
			            }
			        } catch (Exception e2) {
			            e2.printStackTrace();
			        }
			    }
		};
		return ls;
	}
	public GUI() {
		final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.map", "map"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 544);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[417.00,grow][176.00][]", "[][grow]"));
		
		btncreate = new JButton("Create");
		contentPane.add(btncreate, "flowx,cell 0 0");

		btnsave = new JButton("Save");
		contentPane.add(btnsave, "cell 0 0");
		
		btnload = new JButton("Load");
		contentPane.add(btnload, "cell 0 0");
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Repeated Forward A*", "Repeated Backward A*", "Adaptive A*"}));
		contentPane.add(comboBox, "flowx,cell 1 0,growx");
		
		btnStart = new JButton("Start/Next");
		
		contentPane.add(btnStart, "cell 2 0");
		final JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "flowx,cell 0 1 3 1,grow");
		
		btncreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String s=JOptionPane.showInputDialog("Please input N:  (for N*N size of the board)");
					int size= Integer.parseInt(s);
					board = new Board(size);
					board.init();
					int start = board.start;
					int goal = board.goal;
					String[][] str = Board.toString(board);
					header = new String[size];
					for (int i=0;i<size;i++) {
						header[i]="";
					}
					table = Draw(str,header);
					scrollPane.setViewportView(table);
					ActionListener ls = re_save(board,fileChooser);
					ActionListener[] old = btnsave.getActionListeners();
					for(int l=0;l<old.length;l++) {
						btnsave.removeActionListener(old[l]);
					}
					btnsave.addActionListener(ls);
					comboBox.setSelectedIndex(0);
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		
		btnload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = fileChooser.showOpenDialog(getContentPane());
				if (i == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						FileReader reader = new FileReader(file);
						BufferedReader br = new BufferedReader(reader);
						String s = br.readLine();
						int size = Integer.parseInt(s);
						s = br.readLine();
						int start = Integer.parseInt(s);
						s = br.readLine();
						int goal = Integer.parseInt(s);
						String[][] str = new String[size][size];
						header = new String[size];
						for (int r=0;r<size;r++) {
							header[r]="";
							for(int c=0;c<size;c++) {
								str[r][c]="";
							}
						}
						int r = start/size;
						int c = start%size;
						if(start==goal) {
							str[r][c] = "SG";
						}else {
							str[r][c] = "r";
							r = goal/size;
							c = goal%size;
							str[r][c] = "G";
						}
						while((s = br.readLine())!=null) {
								int rc =Integer.parseInt(s);
								r = rc/size;
								c = rc%size;
								str[r][c] ="b";
							
						}
						board = new Board(str,size-1,start,goal);
						table = Draw(str,header);
						scrollPane.setViewportView(table);
						ActionListener ls = re_save(board,fileChooser);
						ActionListener[] old = btnsave.getActionListeners();
						for(int l=0;l<old.length;l++) {
							btnsave.removeActionListener(old[l]);
						}
						btnsave.addActionListener(ls);
						comboBox.setSelectedIndex(0);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board!=null) {
					Res = Board.clone(board);
					Res.find_h();
					String[][] str = Board.toString(Res);
					header = new String[Res.s+1];
					for (int i=0;i<Res.s+1;i++) {
						header[i]="";
					}
					int hval = scrollPane.getHorizontalScrollBar().getValue();
					int val = scrollPane.getVerticalScrollBar().getValue();
					table = Draw(str,header);
					scrollPane.setViewportView(table);
					scrollPane.getVerticalScrollBar().setValue(val);
					Close = new Stack<Node>();
					scrollPane.getHorizontalScrollBar().setValue(hval);
					
				}else {
					JOptionPane.showMessageDialog(null,"Oops there is no maze yet");
					comboBox.setSelectedIndex(0);
				}
			}
		});
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Res != null) {
					int al = comboBox.getSelectedIndex();
					if(al==1) {
						Res = Astar.main(Res);		
					}else if(al==2) {
						Res = Astar.BAStar(Res);
					}else if(al==3) {
						Res = Astar.AAStar(Res,Close);
					}else {
						JOptionPane.showMessageDialog(null,"Please select the A* you want");
					}
					int val = scrollPane.getVerticalScrollBar().getValue();
					int hval = scrollPane.getHorizontalScrollBar().getValue();
					String[][] str = Board.toString(Res);
					table = Draw(str,header);
					scrollPane.setViewportView(table);
					scrollPane.getVerticalScrollBar().setValue(val);
					scrollPane.getHorizontalScrollBar().setValue(hval);
					for(Node[] x:Res.array) {
						for(Node y:x) {
							if(y.type.endsWith(".")) {
								y.type=y.type.replace(".", "");
							}
						}
					}
				}
			}
		});
	}
}
