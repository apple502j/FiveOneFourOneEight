package io.github.apple502j.fiveonefouroneeight.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SimpleOption.DoubleSliderCallbacks.class)
public class DoubleSliderCallbacksMixin {
    @Redirect(method = "codec", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;doubleRange(DD)Lcom/mojang/serialization/Codec;", remap = false))
    private Codec<Double> returnRangelessCodec(double minInclusive, double maxInclusive) {
        return Codec.DOUBLE;
    }
}
