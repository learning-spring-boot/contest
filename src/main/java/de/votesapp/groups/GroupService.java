package de.votesapp.groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

	private final GroupRepository groupRepository;

	@Autowired
	public GroupService(final GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	public Group createOrLoadGroup(final String groupId) {
		Group existingGroup = groupRepository.findOne(groupId);

		if (existingGroup == null) {
			final Group newGroup = new Group(groupId);
			existingGroup = groupRepository.save(newGroup);
		}

		return existingGroup;
	}

	public Group save(final Group group) {
		return groupRepository.save(group);
	}
}
