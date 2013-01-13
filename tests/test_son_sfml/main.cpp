#include <SFML/System.hpp>
#include <SFML/Graphics.hpp>
#include <SFML/Window.hpp>
#include <SFML/Audio.hpp>

#include <cstdlib>
#include <iostream>
#include <string>
#include <sstream>

int main(int argc, char** argv)
{
	sf::Clock clock;
	sf::RenderWindow app(sf::VideoMode(200, 100, 32), "SFML Window");
	sf::Font font;
	sf::Music musique;
	sf::Event event;
	sf::String text;
	
	std::string musicFile("imperial_march.ogg");
	std::string fontFile("font.ttf");
	
	double time = 0.0;
	std::ostringstream oss;
	
	bool error = false;
	
	if(!font.LoadFromFile(fontFile))
	{
		std::cerr << "Erreur: le fichier de police \"" << fontFile << "\"" << std::endl;
		error = true;
	}
	
	else
	{
		text.SetFont(font);
		text.Move(35.0, 30.0);
	}
	
	if(!musique.OpenFromFile(musicFile))
	{
		std::cerr << "Erreur: le fichier son \"" << musicFile << "\"" << std::endl;
		error = true;
	}
	
	else
	{
		time = clock.GetElapsedTime();
		oss << time;
		text.SetText(oss.str());
	}
	
	
	if(!error)
	{
		bool running = true;
		
		musique.Play();
		
		while(running && !error)
		{
			while(app.GetEvent(event))
			{
				switch(event.Type)
				{
					case sf::Event::Closed :
						running = false;
						break;
						
					case sf::Event::KeyPressed :
						if(event.Key.Code == sf::Key::Escape)
						{
							running = false;
							break;
						}
					
					default : ;
				}
			}
			
			app.Clear();
			app.Draw(text);
			app.Display();
		}
	
		musique.Stop();
	}
	
	app.Close();
	
	return EXIT_SUCCESS;
}
