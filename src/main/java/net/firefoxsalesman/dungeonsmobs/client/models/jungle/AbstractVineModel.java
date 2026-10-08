package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractVineEntity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public abstract class AbstractVineModel extends GeoModel<AbstractVineEntity> {

    @Override
    public void setCustomAnimations(AbstractVineEntity entity, long uniqueID, AnimationState<AbstractVineEntity> customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        GeoBone everything = getAnimationProcessor().getBone("everything");

        everything.setHidden(entity.tickCount <= entity.getAnimationTransitionTime());

        for (int i = 1; i < 26; i++) {
            GeoBone part = getAnimationProcessor().getBone("part" + i);
            int partsToShow = 26 - entity.getLengthInSegments();
            if (part != null) {
                part.setHidden(i < partsToShow);
            }
        }

    }

    @Override
    public void applyMolangQueries(AnimationState<AbstractVineEntity> animationState, double currentTick) {
        super.applyMolangQueries(animationState, currentTick);

        AbstractVineEntity vine = animationState.getAnimatable();
        MathParser.setVariable("query.vine_length", vine::getLengthInSegments);
    }
}
