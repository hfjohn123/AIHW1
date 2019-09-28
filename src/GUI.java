import java.awt.EventQueue;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.filechooser.*;
public class GUI extends JFrame{
	private JPanel contentPane;
	private JButton btnload;
	private JButton btnsave;
	private JButton btncreate;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
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
		                if (table.getValueAt(modelRow, modelColumn).equals("2")) {
		                    comp.setBackground(Color.BLACK);
		                    comp.setForeground(Color.BLACK);
		                }else {                                          
		                   comp.setBackground(Color.LIGHT_GRAY);
		                }
		        }
		        return comp;
		    }
		};
		for(int i=0;i<header.length;i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(50);
		}
		table.setRowHeight(50);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);
		return table;
	}
	public ActionListener re_save(String[][] str, int size,JFileChooser fileChooser) {
		ActionListener ls = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            int i = fileChooser.showSaveDialog(null);
			            if(i==JFileChooser.APPROVE_OPTION){
			            	File file = fileChooser.getSelectedFile();
			            	String fname = fileChooser.getName(file);
			            	if(fname.indexOf(".map")==-1){
			    				file=new File(fileChooser.getCurrentDirectory(),fname+".map");
			            	}
			    			file.createNewFile(); 
			    			FileWriter writer = new FileWriter(file);
			    			writer.write(String.valueOf(size)+"\n");
			                for (int r=0;r<size;r++) {
								for (int c= 0;c<size;c++) {
									if(str[r][c].equals("2")) {
										writer.write(String.valueOf(r*size+c)+"\n");
									}
								}
							}
			                writer.flush();
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
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.map", "map"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 544);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		btncreate = new JButton("Create");
		contentPane.add(btncreate, "flowx,cell 0 0");

		btnsave = new JButton("Save");
		contentPane.add(btnsave, "cell 0 0");
		
		btnload = new JButton("Load");
		contentPane.add(btnload, "cell 0 0");
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "flowx,cell 0 1,grow");
		
		btncreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String s=JOptionPane.showInputDialog("Please input the size of the Board:  (n*n)");
					int size= Integer.parseInt(s);
					Board board = new Board(size);
					board = Board.init(board);
					String[][] str = Board.toString(board);
					String[] header = new String[size];
					for (int i=0;i<size;i++) {
						header[i]="";
					}
					table = Draw(str,header);
					scrollPane.setViewportView(table);
					ActionListener ls = re_save(str,size,fileChooser);
					if(btnsave.getActionListeners().length!=0) {
						ActionListener[] old = btnsave.getActionListeners();
						for(int l=0;l<old.length;l++) {
							btnsave.removeActionListener(old[l]);
						}
					}
					btnsave.addActionListener(ls);
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
						String[][] str = new String[size][size];
						String[] header = new String[size];
						for (int r=0;r<size;r++) {
							header[r]="";
							for (int c= 0;c<size;c++) {
								str[r][c] = "0";
							}
						}
						String data;
						while((data = br.readLine())!=null) {
							int rc =Integer.parseInt(data);
							int r = rc/size;
							int c = rc%size;
							str[r][c] ="2";
						}
						table = Draw(str,header);
						scrollPane.setViewportView(table);
						ActionListener ls = re_save(str,size,fileChooser);
						if(btnsave.getActionListeners().length!=0) {
							ActionListener[] old = btnsave.getActionListeners();
							for(int l=0;l<old.length;l++) {
								btnsave.removeActionListener(old[l]);
							}
						}
						btnsave.addActionListener(ls);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
