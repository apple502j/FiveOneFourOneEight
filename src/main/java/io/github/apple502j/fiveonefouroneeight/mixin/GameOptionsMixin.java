package io.github.apple502j.fiveonefouroneeight.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(targets = "net.minecraft.client.option.GameOptions$2")
public class GameOptionsMixin {
	private static final Logger LOGGER = LoggerFactory.getLogger("GameOptionsMixin");

	@Redirect(method = "accept", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V", ordinal = 1))
	private <T> void replaceSetter(Optional<T> value, Consumer<T> originalSetter, String key, SimpleOption<T> option) {
		LOGGER.info("Setting value" + key + "to " + value.orElse(null) + " current " + option.getValue());
		value.ifPresent((realValue) -> {
			if (option.getCallbacks() instanceof SimpleOption.SliderCallbacks) {
				LOGGER.info("Was a slider callback, realValue is " + realValue);
				if (!MinecraftClient.getInstance().isRunning()) {
					option.value = realValue;
					return;
				} 
				if (!Objects.equals(option.getValue(), realValue)) {
					option.value = realValue;
					option.changeCallback.accept(realValue);
				}
				return;
			}
			LOGGER.info("Was not a slider callback");
			originalSetter.accept(realValue);
		});
	}
}
