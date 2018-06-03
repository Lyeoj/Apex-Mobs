package lyeoj.apexmobs.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiMonsterBook extends GuiScreen {
	
	private final int bookImageHeight = 192;
    private final int bookImageWidth = 192;
    private int currPage = 0;
    private static int bookTotalPages;
    private static ArrayList<ResourceLocation> bookPageTextures = new ArrayList<ResourceLocation>();
    private static final ResourceLocation BLANK_PAGE = new ResourceLocation("apexmobs:textures/gui/book/ambook_page.png");
    private static ArrayList<String> bookPageText = new ArrayList<String>();
    private NextPageButton buttonNextPage;
    private NextPageButton buttonPreviousPage;
    private HomePageButton buttonHome;
    private JumpButton buttonJump;
    
    public GuiMonsterBook()
    {
    	bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook.png"));
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook_wanderfeesh.png"));
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook_sniper.png"));
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook_rocketcreeper.png"));
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook_strayguardian.png"));
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook_flinger.png"));
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook_scientist.png"));
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook_hungryslime.png"));
        bookPageTextures.add(BLANK_PAGE);
        bookPageTextures.add(new ResourceLocation("apexmobs:textures/gui/book/ambook_giantsquid.png"));
        bookPageTextures.add(BLANK_PAGE);
        
        bookTotalPages = bookPageTextures.size();
        bookPageText.add("");
        bookPageText.add("        Apex Mobs\n        By Lyeoj\n        Introduction");
        bookPageText.add("        Table of\n        Contents\n-");
        bookPageText.add("\n\n\n\n\n\n\n\nWandering Silverfish\n-------------------\nHabitat: Surface Night\nThreat Level: 2 (Moderate)\n"
        		+ "Status Effects: None\nDrops: Dirty silver shell");
        bookPageText.add("Wandering Silverfish cont.\n-------------------\nA subspecies of silverfish. "
        		+ "Instead of hiding in stone, they can be found wandering on the surface and are tougher due to their exposure to the elements."
        		+ "They often travel in packs to take down their prey, and will use their burrowing ability to reposition themselves in combat.");
        bookPageText.add("\n\n                Sniper\n                Skeleton\n\n\n\n\n\n-------------------\nHabitat: Normal Mob Conditions\nThreat Level: 3 "
        		+ "(Dangerous)\nStatus Effects: None\nDrops: Sniper Arrows, Fragile Sniper Bow (Rare)");
        bookPageText.add("Sniper Skeleton cont.\n-------------------\nA skeleton variant equipped in the latest of undead technology. "
        		+ "Their high-powered bows and scopes allow them to hit targets at a great distance with deadly precision. If a sniper skeleton"
        		+ " is nearby, listen for the unique sound their targeting system makes. If you don't start running at that moment, you will die.");
        bookPageText.add("\n\n                Rocket\n                Creeper\n\n\n\n\n\n-------------------\nHabitat: Normal Mob Conditions\nThreat Level: 1 "
        		+ "(Normal)\nStatus Effects: None\nDrops: Firework Rockets (Flight Duration 5)");
        bookPageText.add("Rocket Creeper cont.\n-------------------\nA creeper subspecies that harnesses its own explosive power to propel itself"
        		+ " skyward. Although they are happy blasting into the sky on their own, they have a tendency to grab anyone nearby when they launch,"
        		+ " carrying their \"passenger\" to an unsafe height. Unless of course the passenger has some form of gliding in which case this relationship becomes mutual.");
        bookPageText.add("\n\n\n\n\n\n\n\nStray Guardian\n-------------------\nHabitat: Ocean\nThreat Level: 2 (Moderate)\n"
        		+ "Status Effects: Waterlogged\nDrops: Aqua Sac");
        bookPageText.add("Stray Guardian cont.\n-------------------\nA guardian subspecies that roams on its own instead of guarding an ocean monument. "
        		+ "They are physically not much different than normal guardians asides from the fact that they can be found anywhere in the ocean. They do "
        		+ "however have the odd ability to weigh down targets hit by their lasers. A condition that could be lethal to air-breathers trying to swim");
        bookPageText.add("\n\n        Shadow\n\n\n\n\n\n-------------------\nHabitat: Normal Mob Conditions\nThreat Level: ??? "
        		+ "\nStatus Effects: Possessed\nDrops: Ectoplasm");
        bookPageText.add("Shadow cont.\n-------------------\nA mysterious spectral mob that will possess other creatures, providing then with buffs. This"
        		+ " makes possessed mobs more dangerous, but when a possessed mob is killed, the shadow will emerge and possess something else, player included. "
        		+ "Multiple shadows can even possess a single entity, giving them immense power, but be warned, this power comes at a cost...");
        bookPageText.add("\n\n              Accelerator\n\n\n\n\n\n\n-------------------\nHabitat: Normal Mob Conditions\nThreat Level: 1 (Normal) "
        		+ "\nStatus Effects: None\nDrops: Magic Stuff");
        bookPageText.add("Accelerator cont.\n-------------------\nA witch variant who controls strange magics allowing it to move entities at high speeds. While it does"
        		+ " not attack directly, it will use its magic to accelerate more dangerous mobs towards the player. Think you are safe indoors? Think again. Accelerators"
        		+ " can even use their magic to phase mobs through walls!");
        bookPageText.add("\n\n                Mad\n                Scientist\n\n\n\n\n\n\n-------------------\nHabitat: Normal Mob Conditions\nThreat Level: 2 (Moderate) "
        		+ "\nStatus Effects: Too many\nDrops: Unique Potions");
        bookPageText.add("Mad scientist cont.\n-------------------\nA villager academic who spent too much time studying rare monsters. Using materials from a variety of"
        		+ " exotic creatures, they have created a myriad of debilitating concoctions and are super eager to test out their effects using nearby humans as unwilling"
        		+ "test subjects. Watch out for the side-effects!");
        bookPageText.add("\n\n\n\n\n\n\n\n\nHungry Slime\n-------------------\nHabitat: Slime chunks, swamps\nThreat Level: Variable, 1-3"
        		+ "\nStatus Effects: None\nDrops: Nutrient Jelly");
        bookPageText.add("Hungry Slime cont.\n-------------------\nThis slime subspecies is composed of a unique substance that allows it to absorb and dissolve organic"
        		+ " material. Unlike normal slimes, these slimes stick to their prey and attempt to dissolve them. Every time a hungry slime dissolves a creature, it will grow"
        		+ " in size, making it increasingly more dangerous. If you spot a small hungry slime, dispatch it immediately before it can feed.");
        bookPageText.add("\n\n\n\n\n\n\n\nGiant Squid\n-------------------\nHabitat: Ocean\nThreat Level: 2 (Moderate)\n"
        		+ "Status Effects: Inked\nDrops: Ink sac");
        bookPageText.add("Giant Squid cont.\n-------------------\nA subspecies of squid that has evolved to be larger and more aggressive. They will swim towards prey"
        		+ " and attempt to grab and drag them to the bottom of the ocean whilst strangling them with their tentacles. When attacked, they will occasionally release"
        		+ " ink to blind their attacker making resistance more difficult.");
 }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() 
    {
    	buttonList.clear();
        Keyboard.enableRepeatEvents(true);
  
        int offsetFromScreenLeft = (width - bookImageWidth) / 2;
        buttonList.add(buttonNextPage = new NextPageButton(1, 
              offsetFromScreenLeft + 120, 179, true));
        buttonList.add(buttonPreviousPage = new NextPageButton(2, 
              offsetFromScreenLeft + 38, 179, false));
        buttonList.add(buttonHome = new HomePageButton(3, offsetFromScreenLeft + 149, 6));
        buttonList.add(buttonJump = new JumpButton(4, offsetFromScreenLeft + 120, 140));
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen() 
    {
        buttonNextPage.visible = (currPage < bookTotalPages - 1);
        buttonPreviousPage.visible = currPage > 0;
        buttonHome.visible = (currPage != 0 && currPage != 1 && currPage != 2);
        buttonJump.visible = currPage == 2;
        super.updateScreen();
    }
 
    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int parWidth, int parHeight, float p_73863_3_)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (currPage == 0)
        {
         mc.getTextureManager().bindTexture(bookPageTextures.get(0));
        }
        else
        {
         mc.getTextureManager().bindTexture(bookPageTextures.get(currPage));
        }
        int offsetFromScreenLeft = (width - bookImageWidth ) / 2;
        drawTexturedModalRect(offsetFromScreenLeft, 2, 0, 0, bookImageWidth, 
              bookImageHeight);
        this.fontRenderer.drawSplitString(bookPageText.get(currPage), 
              offsetFromScreenLeft + 36, 10, 126, 0);
        super.drawScreen(parWidth, parHeight, p_73863_3_);

    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. 
     * Parameters are : mouseX, mouseY, lastButtonClicked & 
     * timeSinceMouseClick.
     */
    @Override
    protected void mouseClickMove(int parMouseX, int parMouseY, 
          int parLastButtonClicked, long parTimeSinceMouseClick) 
    {
     
    }

    @Override
    protected void actionPerformed(GuiButton parButton) 
    {
        if (parButton == buttonNextPage)
        {
            if (currPage < bookTotalPages - 1)
            {
                ++currPage;
            }
        }
        else if (parButton == buttonPreviousPage)
        {
            if (currPage > 0)
            {
                --currPage;
            }
        } else if(parButton == buttonHome) {
        	currPage = 2;
        }
   }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat 
     * events
     */
    @Override
    public void onGuiClosed() 
    {
    	Keyboard.enableRepeatEvents(false);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in 
     * single-player
     */
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton
    {
        private final boolean isNextButton;

        public NextPageButton(int parButtonId, int parPosX, int parPosY, 
              boolean parIsNextButton)
        {
            super(parButtonId, parPosX, parPosY, 23, 13, "");
            isNextButton = parIsNextButton;
        }

        /**
         * Draws this button to the screen.
         */
        @Override
        public void drawButton(Minecraft mc, int parX, int parY, float partialTicks)
        {
            if (visible)
            {
                boolean isButtonPressed = (parX >= this.x
                      && parY >= this.y 
                      && parX < this.x + width 
                      && parY < this.y + height);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(bookPageTextures.get(1));
                int textureX = 0;
                int textureY = 192;

                if (isButtonPressed)
                {
                    textureX += 23;
                }

                if (!isNextButton)
                {
                    textureY += 13;
                }

                drawTexturedModalRect(this.x, this.y, 
                      textureX, textureY, 
                      23, 13);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    static class HomePageButton extends GuiButton
    {

        public HomePageButton(int parButtonId, int parPosX, int parPosY)
        {
            super(parButtonId, parPosX, parPosY, 13, 12, "");
        }

        /**
         * Draws this button to the screen.
         */
        @Override
        public void drawButton(Minecraft mc, int parX, int parY, float partialTicks)
        {
            if (visible)
            {
                boolean isButtonPressed = (parX >= this.x
                      && parY >= this.y 
                      && parX < this.x + width 
                      && parY < this.y + height);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(bookPageTextures.get(1));
                int textureX = 3;
                int textureY = 220;

                if (isButtonPressed)
                {
                    textureX += 24;
                }

                drawTexturedModalRect(this.x, this.y, 
                      textureX, textureY, 
                      13, 12);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    static class JumpButton extends GuiButton
    {

        public JumpButton(int parButtonId, int parPosX, int parPosY)
        {
            super(parButtonId, parPosX, parPosY, 13, 9, "");
        }

        /**
         * Draws this button to the screen.
         */
        @Override
        public void drawButton(Minecraft mc, int parX, int parY, float partialTicks)
        {
            if (visible)
            {
                boolean isButtonPressed = (parX >= this.x
                      && parY >= this.y 
                      && parX < this.x + width 
                      && parY < this.y + height);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(bookPageTextures.get(1));
                int textureX = 3;
                int textureY = 236;

                if (isButtonPressed)
                {
                    textureX += 24;
                }

                drawTexturedModalRect(this.x, this.y, 
                      textureX, textureY, 
                      13, 9);
            }
        }
    }

}
