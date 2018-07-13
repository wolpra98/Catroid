/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content.bricks;

import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.TextView;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

import java.util.List;

public class PlaySoundAndWaitBrick extends PlaySoundBrick {

	private static final long serialVersionUID = 1L;

	public PlaySoundAndWaitBrick() {
	}

	@Override
	public Brick clone() {
		PlaySoundAndWaitBrick clone = new PlaySoundAndWaitBrick();
		clone.setSound(sound);
		return clone;
	}

	@Override
	protected void onViewCreated(View prototypeView) {
		((TextView) view.findViewById(R.id.brick_play_sound_text_view))
				.setText(R.string.brick_play_sound_and_wait);
	}

	@Override
	protected void onPrototypeViewCreated(View prototypeView) {
		((TextView) prototypeView.findViewById(R.id.brick_play_sound_text_view))
				.setText(R.string.brick_play_sound_and_wait);
	}

	@Override
	public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
		sequence.addAction(sprite.getActionFactory().createPlaySoundAction(sprite, sound));

		float duration = 0;

		if (sound != null) {
			MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
			metadataRetriever.setDataSource(sound.getFile().getAbsolutePath());

			duration = Integer.parseInt(metadataRetriever
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000.0f;
		}

		sequence.addAction(sprite.getActionFactory().createWaitAction(sprite, new Formula(duration)));
		return null;
	}
}
