import pygame
import time 
import random
pygame.init()

display_width = 1700
display_height = 1000
black = (0,0,0)
white = (255,255,255)
red = (255,0,0)
car_width = 165
gameDisplay = pygame.display.set_mode((display_width,display_height))
pygame.display.set_caption('Nihal racingCar Game')
clock = pygame.time.Clock()
carImg = pygame.image.load('/Users/admin/nihalgames/car.bmp')
def things_dodged(count):
    font = pygame.font.SysFont(None,25)
    text = font.render("Score: "+str(count), True, black)
    gameDisplay.blit(text, (0,0))
def text_objects(text, font):
    textSurface = font.render(text, True, black)
    return textSurface,textSurface.get_rect()


def message_display(text):

    largeText = pygame.font.Font('freesansbold.ttf',115)
    TextSurf, TextRect = text_objects(text, largeText)
    TextRect.center = ((display_width/2),(display_height/2))
    gameDisplay.blit(TextSurf, TextRect)
    pygame.display.update()

    time.sleep(2)

    game_loop()

def crash():
    message_display('You Crashed')


def things(thingx,thingy,thingw,thingh,color):
    pygame.draw.rect(gameDisplay,color,[thingx,thingy,thingw,thingh])

def car(x,y):
    gameDisplay.blit(carImg, (x,y))

def game_loop():
    x = (display_width * 0.65)
    y = (display_height * 0.8)
    x_change = 0
    y_change = 0

    thing_startx = random.randrange(0,display_width)
    thing_starty = -600
    thing_speed  = 7
    thing_width = 100
    thing_height = 100
    gameExit = False

    dodged = 0
    while not gameExit:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                quit()

            if event.type == pygame.KEYDOWN: 
                if event.key == pygame.K_LEFT:
                    x_change = -5
                elif event.key == pygame.K_RIGHT:
                    x_change = 5
                elif event.key == pygame.K_DOWN:
                    y_change = 5
                elif event.key == pygame.K_UP:
                    y_change = -5 


            if event.type == pygame.KEYUP:
                if event.key == pygame.K_LEFT or event.key == pygame.K_RIGHT:
                    x_change = 0
                elif event.key == pygame.K_UP or event.key == pygame.K_DOWN:
                    y_change = 0
                 
            

        x += x_change
        y += y_change
        color = black
        gameDisplay.fill(white)
        #things(thingx,thingy,thingw,thingh,color)
        things(thing_startx,thing_starty,thing_width,thing_height,color)
        thing_starty += thing_speed
        car(x,y)
        things_dodged(dodged)

        if x > display_width - car_width or x < 0:
            crash()

        if thing_starty > display_height:
            thing_starty = 0 - thing_height
            thing_startx = random.randrange(0,display_width)
            dodged += 1
            thing_width += (dodged * 1.2) 
        if y < thing_starty+thing_height:
            print('y crossover')

            if x > thing_startx and x < thing_startx + thing_width or x+car_width > thing_startx and x+car_width < thing_startx+thing_width:
                print('x crossover')
                crash() 
        pygame.display.update()
        clock.tick(60)

game_loop()
pygame.quit()
quit()
