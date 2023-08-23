package lazyalienserver.carpetlasaddition.logging.Loggers.HopperCoolTimeLogger;

import lazyalienserver.carpetlasaddition.logging.LoggerRegistry;
import lazyalienserver.carpetlasaddition.render.BaseRender;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HopperCoolTimeLogger{
    private static final Map<BlockPos, Integer> hopperBlockCoolTime = new ConcurrentHashMap<>();

    public static void RenderHopperCoolTime() {
        if(isLoggerHopperCoolTime()) {
            BlockPos pos;
            checkState();
            for (Map.Entry<BlockPos, Integer> entry : hopperBlockCoolTime.entrySet()) {
                pos = entry.getKey();
                double x = (double)pos.getX() + 0.5D;
                double y = (double)pos.getY() + 0.5D;
                double z = (double)pos.getZ() + 0.5D;
                if (MinecraftClient.getInstance().player.squaredDistanceTo(x, y, z) > BaseRender.getMaxRenderDistance()*BaseRender.getMaxRenderDistance())
                {
                    hopperBlockCoolTime.remove(entry.getKey());
                    return;
                }
                if (entry.getValue() > 0) {
                    BaseRender.drawString(entry.getValue().toString(), pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, Formatting.RED.getColorValue());
                } else {
                    BaseRender.drawString(entry.getValue().toString(), pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, Formatting.GREEN.getColorValue());
                }
            }
        }
    }

    private static void checkState() {
        for (Map.Entry<BlockPos, Integer> entry : hopperBlockCoolTime.entrySet()) {
            if (entry != null && MinecraftClient.getInstance().world.getBlockState(entry.getKey()).getBlock() == Blocks.HOPPER) {
                continue;
            } else {
                hopperBlockCoolTime.remove(entry.getKey());
            }
        }
    }

    public static Map<BlockPos, Integer> getHopperBlockCoolTime() {
        return hopperBlockCoolTime;
    }

    public static boolean isLoggerHopperCoolTime(){
        return LoggerRegistry.__hopperCoolTime;
    }
}
