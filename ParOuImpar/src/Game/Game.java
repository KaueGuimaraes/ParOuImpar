package Game;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

import java.awt.Canvas;
import java.awt.Color;


public class Game extends Canvas implements Runnable, KeyListener{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private boolean isRunning = true;
	private Thread thread;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int SCALE = 1;
	
	public BufferedImage image;
	
	public String JogadorAtual = "";
	public String Jogador1Escolha = "Null";
	public String Jogador2Escolha = "Null";
	public String Jogador1Number = "";
	public String Jogador2Number = "";
	public String NumberShow = "";
	public String chooser = ">";
	public String Resultado;
	public String Vencedor;
	
	public int Jogador1NumberI;
	public int Jogador2NumberI;
	public int Jogador1Vitorias = 0;
	public int Jogador2Vitorias = 0;
	public int total = 0;
	public int level = 0;
	public int contador;
	public int contadorFinal;
	public int choose;
	
	public boolean down, up, enter;
	public boolean show = true;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		addKeyListener(this);
		initFrame();
		
		/**/
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public void initFrame() {
		frame = new JFrame("Par ou Impar");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public void stop() {
		isRunning = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public void restartGame() {
		level = 0;
		
		Jogador1Escolha = "Null";
		Jogador2Escolha = "Null";
		Jogador1Number = "";
		Jogador2Number = "";
		JogadorAtual = "";
		
		total = 0;
		
		Resultado = "";
		Vencedor = "";
		
		contador = 0;
		contadorFinal = 0;
		choose = 0;
		
		NumberShow = "";
	}
	
	public void tick() {
		contador++;
		
		if(JogadorAtual == "") {
			System.out.println("teste");
			if(new Random().nextInt(2) == 1) {
				//Jogador1
				
				JogadorAtual = "Jogador 1";
			}else {
				//Jogador2
				
				JogadorAtual = "Jogador 2";
			}
		}
		
		if(level == 0) {
			if(up) {
				choose--;
				
				if(choose < 0) {
					choose = 1;
				}
				
				up = false;
			}else if(down) {
				choose++;
				
				if(choose > 1) {
					choose = 0;
				}
				
				down = false;
			}
		}else {
			
		}
		
		if(enter) {
			if(level == 0) {
				if(JogadorAtual == "Jogador 1") {
					if(choose == 0) {
						if(Jogador2Escolha == "Impar" ||
							Jogador2Escolha == "Null") {
							Jogador1Escolha = "Par";
						}else if(Jogador2Escolha == "Par") {
							Jogador1Escolha = "Null";
						}
					}else if(choose == 1) {
						if(Jogador2Escolha == "Par" ||
							Jogador2Escolha == "Null") {
							Jogador1Escolha = "Impar";
						}else if(Jogador2Escolha == "Impar") {
							Jogador1Escolha = "Null";
						}
					}
					
					if(Jogador1Escolha != "Null") {
						JogadorAtual = "Jogador 2";
					}
				}else if(JogadorAtual == "Jogador 2") {
						if(choose == 0) {
							if(Jogador1Escolha == "Impar" ||
								Jogador1Escolha == "Null") {
								Jogador2Escolha = "Par";
							}else if(Jogador1Escolha == "Par") {
								Jogador2Escolha = "Null";
							}
						}else if(choose == 1) {
							if(Jogador1Escolha == "Par" ||
								Jogador1Escolha == "Null") {
								Jogador2Escolha = "Impar";
							}else if(Jogador1Escolha == "Impar") {
								Jogador2Escolha = "Null";
							}
						}
						
						if(Jogador2Escolha != "Null") {
							JogadorAtual = "Jogador 1";
						}
					}

				if(Jogador1Escolha != "Null" &&
					Jogador2Escolha != "Null") {
					level = 1;
				}
			}else if(level == 1) {
				if(JogadorAtual == "Jogador 1") {
					try {
						Jogador1NumberI = Integer.parseInt(Jogador1Number);
						
						if(Jogador2Number == "") {
							JogadorAtual = "Jogador 2";
						}else {
							level = 2;
						}
						
					}catch(Exception e) {
						Jogador1Number = "";
					}
				}else if(JogadorAtual == "Jogador 2") {
					try {
						Jogador2NumberI = Integer.parseInt(Jogador2Number);
						
						if(Jogador1Number == "") {
							JogadorAtual = "Jogador 1";
						}else {
							level = 2;
						}
						
					}catch(Exception e) {
						Jogador2Number = "";
					}
				}
				
				NumberShow = "";
			}else if(level == 3) {
				restartGame();
			}
			
			enter = false;
		}
		
		if(level == 2) {
			total = Jogador1NumberI + Jogador2NumberI;
			
			if(total % 2 == 0) {
				Resultado = "Par";
				
				if(Jogador1Escolha == "Par") {
					Vencedor = "Jogador 1";
				}else {
					Vencedor = "Jogador 2";
				}
			}else {
				Resultado = "Impar";
				
				if(Jogador1Escolha == "Impar") {
					Vencedor = "Jogador 1";
				}else {
					Vencedor = "Jogador 2";
				}
			}
			
			if(Vencedor == "Jogador 1") {
				Jogador1Vitorias++;
			}else {
				Jogador2Vitorias++;
			}
			
			level = 3;
		}else if(level == 3) {
			contadorFinal++;
			
			if(contadorFinal >= 50) {
				if(show) {
					show = false;
				}else {
					show = true;
				}
				
				contadorFinal = 0;
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*RENDERIZAÇÃO DO JOGO*/
		//Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.BLUE);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString("Jogador 1 = " + Jogador1Escolha + " " + Jogador1Vitorias, 20 * Game.SCALE, 10 * Game.SCALE);
		
		g.setColor(Color.RED);
		g.drawString("Jogador 2 = " + Jogador2Escolha + " " + Jogador2Vitorias, (WIDTH - 98) * Game.SCALE, (HEIGHT - 4) * Game.SCALE);
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("arial", Font.BOLD, 100));
		
		if(level == 0) {
			if(contador >= 20) {
				if(contador <= 80) {
					g.drawString(".", 580 * Game.SCALE, 230 * Game.SCALE);
				}else if(contador <= 140) {
					g.drawString("..", 580 * Game.SCALE, 230 * Game.SCALE);
				}else if(contador <= 200) {
					g.drawString("...", 580 * Game.SCALE, 230 * Game.SCALE);
				}else {
					if(JogadorAtual == "Jogador 1") {
						g.setColor(Color.BLUE);
					}else {
						g.setColor(Color.RED);
					}
					g.drawString(JogadorAtual, 410 * Game.SCALE, 230 * Game.SCALE);
					
					g.setColor(Color.GRAY);
					g.setFont(new Font("arial", Font.BOLD, 40));
					g.drawString("Par", 410 * Game.SCALE, 280 * Game.SCALE);
					g.drawString("Impar", 410 * Game.SCALE, 320 * Game.SCALE);
					
					if(JogadorAtual == "Jogador 1") {
						g.setColor(Color.BLUE);
					}else {
						g.setColor(Color.RED);
					}
					
					if(choose == 0) {
						g.drawString(chooser, (410 - 40) * Game.SCALE, 280 * Game.SCALE);
					}else if(choose == 1) {
						g.drawString(chooser, (410 - 40) * Game.SCALE, 320 * Game.SCALE);
					}
				}
			}
		}else if(level == 1) {
			if(JogadorAtual == "Jogador 1") {
				g.setColor(Color.BLUE);
			}else {
				g.setColor(Color.RED);
			}
			
			
			g.drawString(JogadorAtual, 410 * Game.SCALE, 230 * Game.SCALE);
			
			g.setColor(Color.YELLOW);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString(NumberShow, 410 * Game.SCALE, 290 * Game.SCALE);
		}else if(level == 2 ||
				level == 3) {
			g.setFont(new Font("arial", Font.BOLD, 40));
			
			g.setColor(Color.BLUE);
			g.drawString("Jogador 1:   " + Jogador1NumberI + ' ' + Jogador1Escolha, 300 * Game.SCALE, 245 * Game.SCALE);
			
			g.setColor(Color.RED);
			g.drawString("Jogador 2:   " + Jogador2NumberI + ' ' + Jogador2Escolha, 300 * Game.SCALE, 290 * Game.SCALE);
			
			g.setColor(Color.GREEN);
			g.drawString("Total: " + total, 300 * Game.SCALE, 355 * Game.SCALE);
			
			g.setColor(Color.YELLOW);
			g.drawString("Resultado: " + Resultado, 300 * Game.SCALE, 420 * Game.SCALE);
			g.drawString("Vencedor: " + Vencedor, 300 * Game.SCALE, 465 * Game.SCALE);
			
			if(show) {
				g.setColor(Color.ORANGE);
				g.drawString("> Pressione ENTER para jogar novamente", 300 * Game.SCALE, 530 * Game.SCALE);
			}
		}
		
		/***/
		
		g.dispose();
		g = bs.getDrawGraphics();
		
		//drawRectExample(xx, yy);
		
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		bs.show();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(contador > 200) {
			if(e.getKeyCode() == KeyEvent.VK_DOWN ||
					e.getKeyCode() == KeyEvent.VK_S) {
				down = true;
			}else if(e.getKeyCode() == KeyEvent.VK_UP ||
						e.getKeyCode() == KeyEvent.VK_W) {
				up = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				enter = true;
			}
		}
		
		if(level == 1) {
			if(JogadorAtual == "Jogador 1") {
				if(Jogador1Number.length() <= 27) {
					if(e.getKeyCode() == KeyEvent.VK_0) {
						Jogador1Number += "0";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_1) {
						Jogador1Number += "1";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_2) {
						Jogador1Number += "2";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_3) {
						Jogador1Number += "3";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_4) {
						Jogador1Number += "4";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_5) {
						Jogador1Number += "5";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_6) {
						Jogador1Number += "6";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_7) {
						Jogador1Number += "7";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_8) {
						Jogador1Number += "8";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_9) {
						Jogador1Number += "9";
						NumberShow += "*";
					}
				}
				
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					Jogador1Number = "";
					NumberShow = "";
				}
			}
			
			if(JogadorAtual == "Jogador 2") {
				if(Jogador2Number.length() <= 27) {
					if(e.getKeyCode() == KeyEvent.VK_0) {
						Jogador2Number += "0";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_1) {
						Jogador2Number += "1";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_2) {
						Jogador2Number += "2";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_3) {
						Jogador2Number += "3";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_4) {
						Jogador2Number += "4";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_5) {
						Jogador2Number += "5";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_6) {
						Jogador2Number += "6";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_7) {
						Jogador2Number += "7";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_8) {
						Jogador2Number += "8";
						NumberShow += "*";
					}
					
					if(e.getKeyCode() == KeyEvent.VK_9) {
						Jogador2Number += "9";
						NumberShow += "*";
					}
				}
				
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					Jogador2Number = "";
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
