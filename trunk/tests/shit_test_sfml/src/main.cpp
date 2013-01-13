/**
	@file main.cpp
	@brief PPG test. Shamelessly copied on SFML1.6 graphics-sprite.test
	@author ParPainG team
	@date 2012/10/31
*/

#include <sstream>
#include <SFML/Graphics.hpp>
//#include "Macros.hpp"

static const int WIDTH = 800;
static const int HEIGHT = 600;

int main(int argc, const char* argv[])
{
	// Command line args
	int nbSprites = 42;
	if (argc >= 2)
		nbSprites = atoi(argv[1]);
	int speedCoeff = 1000;
	if (argc >= 3)
		speedCoeff = atoi(argv[2]);
	
	srand(42);

    // Create the main rendering window
    sf::RenderWindow app(sf::VideoMode(WIDTH, 600, 32), "SFML Graphics");

    // Load the sprite image from a file
    sf::Texture texture;
    if (!texture.loadFromFile("data/turd_alpha.png"))
        return EXIT_FAILURE;

	std::vector<sf::Sprite> turds(nbSprites, sf::Sprite(texture));
	std::vector<sf::Vector2f> turds_velocities(nbSprites);
	
	// Init positions
	for (int i = 0; i < nbSprites; ++i) {
		turds_velocities[i].x = (2 * (float)rand() / (float)RAND_MAX - 1) * (float)speedCoeff;
		turds_velocities[i].y = (2 * (float)rand() / (float)RAND_MAX - 1) * (float)speedCoeff;
		turds[i].setPosition((float)WIDTH * (float)rand() / (float)RAND_MAX, (float)HEIGHT * (float)rand() / (float)RAND_MAX);
	}
	
	sf::Font font;
	font.loadFromFile("data/arial.ttf");
	sf::Text fps;
	fps.setFont(font);
	sf::Clock clock;
	std::stringstream sFps;
	sFps.precision(0);
	sFps.flags(std::ios::fixed);

    // Start game loop
    while (app.isOpen())
    {
        // Process events
        sf::Event event;
        while (app.pollEvent(event))
        {
            // Close window : exit
            if (event.type == sf::Event::Closed)
                app.close();
        }

        // Clear screen
        app.clear();

		float secondsSinceLastFrame = clock.restart().asSeconds();

		// Update turds
		for (int i = 0; i < nbSprites; ++i) {
			sf::Vector2f p = turds[i].getPosition();
			sf::Vector2f v = turds_velocities[i];
			p.x += v.x * secondsSinceLastFrame;
			p.y += v.y * secondsSinceLastFrame;
			// Clipping
			if (p.x > WIDTH)
				p.x -= WIDTH;
			if (p.x < 0)
				p.x += WIDTH;
			if (p.y > HEIGHT)
				p.y -= HEIGHT;
			if (p.y < 0)
				p.y += HEIGHT;
			turds[i].setPosition(p);
			// Display sprite in our window
			app.draw(turds[i]);
		}
					
		//FPS
		sFps.str("");
		sFps << "fps: " << 1.f / secondsSinceLastFrame;
		fps.setString(sFps.str());
        app.draw(fps);

        // Display window contents on screen
        app.display();
    }

    return EXIT_SUCCESS;
}
