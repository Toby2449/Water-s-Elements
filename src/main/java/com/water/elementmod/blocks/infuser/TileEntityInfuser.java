package com.water.elementmod.blocks.infuser;

import java.util.Map;
import java.util.Random;

import com.water.elementmod.blocks.synthesizer.BlockSynthesizer;
import com.water.elementmod.blocks.synthesizer.SynthesizerRecipes;
import com.water.elementmod.init.EmItems;
import com.water.elementmod.util.Utils;
import com.water.elementmod.util.handlers.EmSoundHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityInfuser extends TileEntity implements ITickable, IInventory
{
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);
	private String customName;
	
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime = 800;
	private int compatible;
	Random randnum;

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.infuser";
	}
	
	@Override
	public boolean hasCustomName() 
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}
	
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.inventory.size();
	}
	
	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : this.inventory)
		{
			if(!stack.isEmpty()) return false;
		}
		return true;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return (ItemStack)this.inventory.get(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.inventory, index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = (ItemStack)this.inventory.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.inventory.set(index, stack);
		
		if(stack.getCount() > this.getInventoryStackLimit())
			stack.setCount(this.getInventoryStackLimit());
		if(index == 0 && index + 1 == 1 && !flag) 
		{
			ItemStack stack1 = (ItemStack)this.inventory.get(index + 1);
			this.totalCookTime = this.getCookTime(stack, stack1);
			this.cookTime = 0;
			this.markDirty();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.inventory);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = getItemBurnTime((ItemStack)this.inventory.get(2));
		this.compatible = compound.getInteger("compatible");
		
		if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short)this.burnTime);
		compound.setInteger("CookTime", (short)this.cookTime);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
		compound.setInteger("compatible", (short)this.compatible);
		ItemStackHelper.saveAllItems(compound, this.inventory);
		
		if(this.hasCustomName()) compound.setString("CustomName", this.customName);
		return compound;
	}
	
	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}
	
	public boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	
	public boolean isCooking() 
	{
		return this.cookTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isCompatible(IInventory inventory) 
	{
		if(inventory.getField(4) == 0) return false;
		if(inventory.getField(4) == 1) return true;
		return false; // just incase
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) 
	{
		return inventory.getField(0) > 0;
	}
	
	public void update() 
	{	
		boolean flag = this.isBurning();
		boolean flag1 = false;
		
		if(this.isBurning()) --this.burnTime;
		
		if(!this.world.isRemote)
		{
			ItemStack check = (ItemStack)this.inventory.get(0);
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(check);
			if(map.get(Enchantments.FIRE_ASPECT) != null)
			{
				this.setField(4, 0);
			} 
			else
			{
				this.setField(4, 1);
			}
			
			ItemStack stack = (ItemStack)this.inventory.get(2);
		
			if(this.isBurning() || !stack.isEmpty() && !((((ItemStack)this.inventory.get(0)).isEmpty()) || ((ItemStack)this.inventory.get(1)).isEmpty()))
			{
				if(!this.isBurning() && this.canSmelt())
				{
					this.burnTime = getItemBurnTime(stack);
					this.currentBurnTime = burnTime;
					
					if(this.isBurning())
					{
						flag1 = true;
						if(!stack.isEmpty())
						{
							Item item = stack.getItem();
							stack.shrink(1);
							
							if(stack.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(stack);
								this.inventory.set(2, item1);
							}
						}
					}
				}
			}
			
			if(this.isBurning() && this.canSmelt())
			{
				++this.cookTime;
				
				if(this.cookTime == this.totalCookTime)
				{
					this.cookTime = 0;
					this.totalCookTime = this.getCookTime((ItemStack)this.inventory.get(0), (ItemStack)this.inventory.get(1));
					this.smeltItem();
					flag1 = true;
				}
			}
			else this.cookTime = 0;
		}
		else if(!this.isBurning() && this.cookTime > 0)
		{
			this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
		}
		if(flag != this.isBurning())
		{
			flag1 = true;
			if(this.inventory.get(1).getItem() == EmItems.FIRESMPL) BlockInfuser.setElement(1);
			if(this.inventory.get(1).getItem() == EmItems.WATERDRP) BlockInfuser.setElement(0);
			if(this.inventory.get(1).getItem() == EmItems.NATURESMPL) BlockInfuser.setElement(2);
			BlockInfuser.setState(this.isCooking(), this.world, this.pos);
		}
	}
	
	public int getCookTime(ItemStack input1, ItemStack input2)
	{
		return 800;
	}
	
	private boolean canSmelt() 
	{
		if(((ItemStack)this.inventory.get(0)).isEmpty() || ((ItemStack)this.inventory.get(1)).isEmpty()) return false;
		else 
		{
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments((ItemStack)this.inventory.get(0));
			if(map.get(Enchantments.FIRE_ASPECT) != null)
			{
				this.setField(4, 0);
				return false;
			} 
			else 
			{
				this.setField(4, 1);
				ItemStack result = InfuserRecipes.getInstance().getInfuserResult((ItemStack)this.inventory.get(0), (ItemStack)this.inventory.get(1));	
				if(result.isEmpty()) return false;
				else
				{
					ItemStack output = (ItemStack)this.inventory.get(3);
					if(output.isEmpty()) return true;
					if(!output.isItemEqual(result)) return false;
					int res = output.getCount() + result.getCount();
					return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
				}
			}
		}
	}
	
	public void smeltItem()
	{
		if(this.canSmelt())
		{
			ItemStack input1 = (ItemStack)this.inventory.get(0);
			Integer inputDamage = this.inventory.get(0).getItemDamage();
			ItemStack input2 = (ItemStack)this.inventory.get(1);
			ItemStack result = InfuserRecipes.getInstance().getInfuserResult(input1, input2);
			ItemStack output = (ItemStack)this.inventory.get(3);
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(input1);
			
			result.setItemDamage(inputDamage);
			EnchantmentHelper.setEnchantments(map, result);
			if(output.isEmpty()) this.inventory.set(3, result.copy());
			
			if(input2.getItem() == EmItems.FIRESMPL) this.world.playSound((EntityPlayer)null, this.getPos(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.7F + 0.3F);
			if(input2.getItem() == EmItems.WATERDRP) this.world.playSound((EntityPlayer)null, this.getPos(), SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() + 0.5F);
			if(input2.getItem() == EmItems.NATURESMPL) this.world.playSound((EntityPlayer)null, this.getPos(), EmSoundHandler.NATURE_LEAVES, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
			this.world.playSound((EntityPlayer)null, this.getPos(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
			
			input1.shrink(1);
			input2.shrink(1);
		}
		
	}
	
	public static int getItemBurnTime(ItemStack fuel) 
	{
		if(fuel.isEmpty()) return 0;
		else 
		{
			Item item = fuel.getItem();

			
			if (item == EmItems.VOIDSING) return 800;

			return GameRegistry.getFuelValue(fuel);
		}
	}
		
	public static boolean isItemFuel(ItemStack fuel)
	{
		return getItemBurnTime(fuel) > 0;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}
	
	@Override
	public void closeInventory(EntityPlayer player) {}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if(index == 2) return false;
		else if(index != 1) return false;
		else
		{
			return isItemFuel(stack);
		}
	}
	
	public String getGuiID()
	{
		return "em:infuser";
	}
	
	public int getField(int id) 
	{
		switch(id) 
		{
		case 0:
			return this.burnTime;
		case 1:
			return this.currentBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		case 4:
			return this.compatible;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) 
	{
		switch(id) 
		{
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		case 4:
			this.compatible = value;
			break;
		}
	}

	@Override
	public int getFieldCount() 
	{
		return 5;
	}

	@Override
	public void clear() 
	{
		this.inventory.clear();
	}

}
