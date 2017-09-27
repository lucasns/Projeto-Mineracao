package cin.mineracao;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.lucene.queryparser.classic.ParseException;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public final class Application extends JFrame {
	private static final long serialVersionUID = -3546437344310815854L;
	private JPanel contentPane;
	private JLabel totalResultsLabel;
	private String[] results;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application frame = new Application();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}

	public Application() {
		results = new String[0];
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		setType(Type.NORMAL);
		setResizable(false);
		setTitle("Recupera\u00E7\u00E3o de Informa\u00E7\u00F5es");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 600);
		setContentPane(contentPane);
		
		final JCheckBox stopwordsBox = new JCheckBox("Stopwords");
		stopwordsBox.setBounds(11, 0, 150, 40);
		
		contentPane.add(stopwordsBox);
		
		final JCheckBox steemingBox = new JCheckBox("Steeming");
		steemingBox.setBounds(210, 0, 150, 40);
		contentPane.add(steemingBox);
		
		final JTextField queryField = new JTextField("");
		queryField.setToolTipText("Digite a sua busca");
		queryField.setBounds(11, 48, 613, 31);

		contentPane.add(queryField);
		
		JScrollPane scrollPane = new JScrollPane();
		final JList<String> docsList = new JList<String>();
		docsList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
		        	if (Desktop.isDesktopSupported()) {
		        	    try {
		        	        File myFile = new File("files", docsList.getSelectedValue()+".pdf");
		        	        Desktop.getDesktop().open(myFile);
		        	    } catch (IOException ex) {
		        	        System.out.println("Couldn't open the file");
		        	    }
		        	}
		        }
			}
		});
		
		scrollPane.setBounds(11, 95, 768, 449);
		scrollPane.setViewportView(docsList);
		
		contentPane.add(scrollPane);
		
		totalResultsLabel = new JLabel("Results: ");
		totalResultsLabel.setBounds(400, 541, 100	, 30);
		contentPane.add(totalResultsLabel);
		
		
		JButton searchButton = new JButton("Procurar");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				final CustomAnalyzer analyzer = new CustomAnalyzer(stopwordsBox.isSelected(), steemingBox.isSelected());
				
				final String queryString = queryField.getText();
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							results = Engine.search(queryString, analyzer);
						} catch (ParseException | IOException e) {
							System.out.println(e.getMessage());
						}
						
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								docsList.setListData(results);
								totalResultsLabel.setText("Results: "+results.length);
							}
						});
					}
				}).start();
			}
		});
		searchButton.setBounds(629, 48, 150, 31);
		
		contentPane.add(searchButton);
	}
}