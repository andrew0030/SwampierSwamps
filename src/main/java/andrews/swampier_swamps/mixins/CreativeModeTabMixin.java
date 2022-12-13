package andrews.swampier_swamps.mixins;

//@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin
{
//    @Shadow private Collection<ItemStack> displayItems;
//    @Shadow private Set<ItemStack> displayItemsSearchTab;
//
//    @Inject(method = "buildContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;rebuildSearchTree()V"))
//    public void injectBuild(FeatureFlagSet flagSet, boolean hasPermissions, CallbackInfo ci)
//    {
//        if(((CreativeModeTab)(Object)this) == CreativeModeTabs.FOOD_AND_DRINKS)
//        {
//            ArrayList<ItemStack> list = new ArrayList<>();
//            for(ItemStack stack : displayItems)
//            {
//                list.add(stack);
//                if(stack.is(Items.COOKED_RABBIT))
//                {
//                    list.add(new ItemStack(SSItems.FROG_LEG.get()));
//                    list.add(new ItemStack(SSItems.COOKED_FROG_LEG.get()));
//                    displayItemsSearchTab.add(new ItemStack(SSItems.FROG_LEG.get()));
//                    displayItemsSearchTab.add(new ItemStack(SSItems.COOKED_FROG_LEG.get()));
//                }
//            }
//            displayItems = list;
//        }
//    }
}