package io.github.apple502j.fiveonefouroneeight.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleOption.ValidatingIntSliderCallbacks.class)
public class ValidatingIntSliderCallbacksMixin {
    @Inject(method = "codec", at = @At("HEAD"), cancellable = true)
    private void returnRangelessCodec(CallbackInfoReturnable<Codec<Integer>> cir) {
        cir.setReturnValue(Codec.INT);
    }
}
